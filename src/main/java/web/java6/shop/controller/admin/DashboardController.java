package web.java6.shop.controller.admin;

import web.java6.shop.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("tongDoanhThu", dashboardService.getTongDoanhThu());
        model.addAttribute("tongDonHang", dashboardService.getTongDonHang());
        model.addAttribute("tongKhachHang", dashboardService.getTongKhachHang());
        model.addAttribute("donHangGanDay", dashboardService.getDonHangGanDay());
        return "admin/dashboard";
    }
}