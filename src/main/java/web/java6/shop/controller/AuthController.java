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

            // Nếu chọn "ghi nhớ" thì lưu cookie (CHỈ lưu username)
            if ("on".equals(remember)) {
                Cookie ckUser = new Cookie("username", idUser);
                ckUser.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                ckUser.setPath("/");
                response.addCookie(ckUser);
            } else {
                // Xóa cookie nếu không chọn "ghi nhớ"
                Cookie ckUser = new Cookie("username", null);
                ckUser.setMaxAge(0);
                ckUser.setPath("/");
                response.addCookie(ckUser);
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
