package web.java6.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.java6.shop.service.UserService;
import web.java6.shop.service.SanPhamService;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("userCount", userService.findAll().size());
        model.addAttribute("productCount", sanPhamService.findAll().size());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String showUserManagement(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/products")
    public String showProductManagement(Model model) {
        model.addAttribute("products", sanPhamService.findAll());
        return "admin/products";
    }
}
