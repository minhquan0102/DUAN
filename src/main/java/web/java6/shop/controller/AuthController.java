package web.java6.shop.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import web.java6.shop.model.User;
import web.java6.shop.service.UserService;

import jakarta.servlet.http.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @Autowired
    private web.java6.shop.Config.EmailService emailService;

    // Xử lý đăng ký

    // Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Kiểm tra email đã tồn tại chưa
        if (userService.findByIdUser(user.getIdUser()) != null) {
            model.addAttribute("error", "Email đã được đăng ký!");
            return "register";
        }

        // Set các thông tin mặc định
        user.setVaitro(false); // user bình thường
        user.setTrangthai(true); // active
        user.setNgaydangky(LocalDate.now());
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());

        userService.save(user);
        emailService.sendSimpleMail(user.getIdUser(),
                "Chúc mừng đăng ký!",
                "Cảm ơn bạn đã đăng ký tài khoản tại website của chúng tôi!");

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request, Model model) {
        String username = "";
        boolean remember = false;

        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("username".equals(c.getName())) {
                    username = c.getValue();
                    remember = true;
                }
            }
        }

        model.addAttribute("username", username);
        model.addAttribute("remember", remember);
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam("idUser") String idUser,
            @RequestParam("matkhau") String password,
            @RequestParam(value = "remember", required = false) String remember,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        Optional<User> optionalUser = userService.findById(idUser);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getMatkhau().equals(password)) {
                // Lưu session
                session.setAttribute("user", user);

                // Lưu cookie username nếu đánh dấu "remember me"
                Cookie ckUser = new Cookie("username", "on".equals(remember) ? idUser : null);
                ckUser.setMaxAge("on".equals(remember) ? 7 * 24 * 60 * 60 : 0); // 7 ngày hoặc xóa cookie
                ckUser.setPath("/");
                response.addCookie(ckUser);

                // Spring Security auth
                String role = user.isVaitro() ? "ROLE_ADMIN" : "ROLE_USER";
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        idUser, null, Collections.singleton(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(auth);

                // Redirect
                return user.isVaitro() ? "redirect:/admin/dashboard" : "redirect:/home";
            }
        }

        model.addAttribute("error", "Sai thông tin đăng nhập");
        return "login";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
}