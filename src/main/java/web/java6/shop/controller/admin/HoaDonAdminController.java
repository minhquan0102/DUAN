package web.java6.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import web.java6.shop.model.HoaDon;
import web.java6.shop.model.HoaDonChiTiet;
import web.java6.shop.model.User;
import web.java6.shop.service.HoaDonChiTietService;
import web.java6.shop.service.HoaDonService;

import java.util.List;

@Controller
@RequestMapping("/admin/hoadon")
public class HoaDonAdminController {

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @GetMapping
public String danhSachHoaDon(Model model) {
    List<HoaDon> hoaDons = hoaDonService.findAll();
    model.addAttribute("hoaDons", hoaDons);

    // Biến cho layout
    model.addAttribute("pageTitle", "Quản lý hóa đơn");
    model.addAttribute("activePage", "hoadon");
    model.addAttribute("content", "admin/QLHoaDon"); // file con để chèn vào layout

    // Nút tìm kiếm
    model.addAttribute("searchAction", "/admin/hoadon");
    return "admin/layout"; // Luôn trả về layout.html
}
    // Xử lý cập nhật
@PostMapping("/updateStatus/{idHoaDon}")
public String updateOrderStatus(@PathVariable Integer idHoaDon,
                                @RequestParam("status") String status) {
    hoaDonService.updateOrderStatus(idHoaDon, status);
    return "redirect:/admin/hoadon"; // reload danh sách
}



}
