package web.java6.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import web.java6.shop.repository.HoaDonChiTietRepository;

@Controller
@RequestMapping("/admin/hoadon")
public class HoadonchitietController {

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @GetMapping("/admin/hoadon/view/{id}")
public String xemChiTietHoaDon(@PathVariable("id") Integer idHoaDon, Model model) {
    model.addAttribute("chitietList", hoaDonChiTietRepository.findByIdHoaDon(idHoaDon));
    model.addAttribute("hoaDonId", idHoaDon);

    // Thêm các biến cho layout
    model.addAttribute("pageTitle", "Chi tiết hóa đơn #" + idHoaDon);
    model.addAttribute("activePage", "hoadon");
    model.addAttribute("content", "admin/hoadonchitiet"); // file con

    return "layout"; // luôn trả về layout.html
}

}
