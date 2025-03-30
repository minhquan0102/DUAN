package web.java6.shop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.java6.shop.model.User;
import web.java6.shop.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // file Thymeleaf: register.html
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Thực hiện kiểm tra hợp lệ, mã hóa mật khẩu nếu cần
        userService.save(user);
        return "redirect:/login";
    }

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login"; // file Thymeleaf: login.html
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String loginUser(@RequestParam("idUser") String idUser,
                            @RequestParam("matkhau") String password,
                            Model model) {
        Optional<User> optionalUser = userService.findById(idUser);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Giả sử mật khẩu lưu dưới dạng plain text
            if (user.getMatkhau().equals(password)) {
                // Phân quyền: nếu vaitro là true -> Admin, false -> User
                if (user.isVaitro()) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/user/home";
                }
            }
        }
        model.addAttribute("error", "Sai thông tin đăng nhập");
        return "login";
    }
}
