package web.java6.shop.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/home")
    public String userHome(Model model) {
        model.addAttribute("message", "Chào mừng Quý khách!");
        return "user/home"; // Tương ứng với file templates/user/home.html
    }
}
