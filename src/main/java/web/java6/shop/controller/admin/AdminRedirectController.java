package web.java6.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminRedirectController {

    @GetMapping("/admin")
    public String redirectToDashboard() {
        return "redirect:/admin/dashboard";
    }
}
