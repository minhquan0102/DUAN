package web.java6.shop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.java6.shop.model.User;
import web.java6.shop.service.UserService;

import jakarta.servlet.http.HttpSession; 


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
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        userService.save(user);
        return "redirect:/login";
    }

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

// Xử lý đăng nhập
@PostMapping("/login")
public String loginUser(@RequestParam("idUser") String idUser,
                        @RequestParam("matkhau") String password,
                        HttpSession session, // thêm HttpSession ở đây
                        Model model) {
    Optional<User> optionalUser = userService.findById(idUser);
    if(optionalUser.isPresent()){
        User user = optionalUser.get();
        if(user.getMatkhau().equals(password)){
            // Lưu user vào session
            session.setAttribute("user", user);

            if(user.isVaitro()){
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/products";
            }
        }
    }
    model.addAttribute("error", "Sai thông tin đăng nhập");
    return "login";
}

@GetMapping("/logout")
    public String logoutUser() {
        return "redirect:/login";
    }
}
