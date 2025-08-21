package web.java6.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.java6.shop.model.HoaDon;
import web.java6.shop.model.HoaDonChiTiet;
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
        return "admin/QLHoaDon";
    }

}
