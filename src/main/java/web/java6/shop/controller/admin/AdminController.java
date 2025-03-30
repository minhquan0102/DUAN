package web.java6.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        model.addAttribute("message", "Chào mừng Admin!");
        return "admin/home"; // Tương ứng với file templates/admin/home.html
    }
}
