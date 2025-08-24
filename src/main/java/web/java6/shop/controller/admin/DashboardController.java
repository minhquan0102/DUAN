package web.java6.shop.controller.admin;

import web.java6.shop.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import web.java6.shop.model.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
@ModelAttribute
public void addUserToModel(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
        model.addAttribute("avatar", user.getHinh());
        model.addAttribute("hoten", user.getHoten());
    }
}
   @GetMapping("/admin/dashboard")
public String dashboard(Model model) {
    model.addAttribute("tongDoanhThu", dashboardService.getTongDoanhThu());
    model.addAttribute("tongDonHang", dashboardService.getTongDonHang());
    model.addAttribute("tongKhachHang", dashboardService.getTongKhachHang());
    model.addAttribute("donHangGanDay", dashboardService.getDonHangGanDay());
    model.addAttribute("tongHangSapHet", dashboardService.getTongHangSapHet());
    model.addAttribute("sanPhamSapHet", dashboardService.getSanPhamSapHet());
    // Thêm các biến cho layout
    model.addAttribute("pageTitle", "Tổng quan");
    model.addAttribute("activePage", "dashboard");
    model.addAttribute("content", "admin/dashboard"); // file con để chèn vào layout

    return "admin/layout"; // LUÔN trả về layout.html
}
}
