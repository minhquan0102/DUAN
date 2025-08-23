package web.java6.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.java6.shop.model.DanhGia;
import web.java6.shop.service.DanhGiaService;

import java.util.List;

@Controller
@RequestMapping("/admin/danhgia")
public class AdminDanhGiaController {

    @Autowired
    private DanhGiaService danhGiaService;

    @GetMapping
public String listDanhGia(Model model,
                          @RequestParam(value = "keyword", required = false) String keyword) {
    List<DanhGia> danhgias = (keyword != null && !keyword.isEmpty())
            ? danhGiaService.search(keyword)
            : danhGiaService.findAll();
    model.addAttribute("danhgias", danhgias);
    model.addAttribute("pageTitle", "Quản lý đánh giá");
    model.addAttribute("activePage", "danhgia");
    model.addAttribute("content", "admin/QLDanhgia"); // trang con

    return "admin/layout"; // Luôn trả về layout
}
    @PostMapping("/duyet/{id}")
    public String duyetDanhGia(@PathVariable("id") Integer id) {
        danhGiaService.duyet(id);
        return "redirect:/admin/danhgia";
    }

    @PostMapping("/an/{id}")
    public String anDanhGia(@PathVariable("id") Integer id) {
        danhGiaService.an(id);
        return "redirect:/admin/danhgia";
    }

    @PostMapping("/xoa/{id}")
    public String xoaDanhGia(@PathVariable("id") Integer id) {
        danhGiaService.delete(id);
        return "redirect:/admin/danhgia";
    }
}
