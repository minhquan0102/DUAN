package web.java6.shop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/login";
    }

    // Hiển thị trang đăng nhập (đọc cookie nếu có)
    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request, Model model) {
        String username = "";
        String password = "";
        boolean remember = false;

        // Lấy cookie
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals("username")) {
                    username = c.getValue();
                    remember = true;
                }
                if (c.getName().equals("password")) {
                    password = c.getValue();
                }
            }
        }

        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("remember", remember);
        return "login";
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String loginUser(@RequestParam("idUser") String idUser,
                            @RequestParam("matkhau") String password,
                            @RequestParam(value = "remember", required = false) String remember,
                            HttpSession session,
                            HttpServletResponse response,
                            Model model) {
        Optional<User> optionalUser = userService.findById(idUser);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getMatkhau().equals(password)){
                // Lưu user vào session
                session.setAttribute("user", user);

                // Nếu chọn "ghi nhớ" thì lưu cookie
                if ("on".equals(remember)) {
                    Cookie ckUser = new Cookie("username", idUser);
                    Cookie ckPass = new Cookie("password", password); 
                    ckUser.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                    ckPass.setMaxAge(7 * 24 * 60 * 60);
                    ckUser.setPath("/");
                    ckPass.setPath("/");
                    response.addCookie(ckUser);
                    response.addCookie(ckPass);
                } else {
                    // Xóa cookie nếu không chọn "ghi nhớ"
                    Cookie ckUser = new Cookie("username", null);
                    ckUser.setMaxAge(0);
                    ckUser.setPath("/");
                    Cookie ckPass = new Cookie("password", null);
                    ckPass.setMaxAge(0);
                    ckPass.setPath("/");
                    response.addCookie(ckUser);
                    response.addCookie(ckPass);
                }

                if(user.isVaitro()){
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/home";
                }
            }
        }
        model.addAttribute("error", "Sai thông tin đăng nhập");
        return "login";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
