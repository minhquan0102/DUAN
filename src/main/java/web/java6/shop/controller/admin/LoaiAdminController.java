package web.java6.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import web.java6.shop.model.Loai;
import web.java6.shop.model.User;
import web.java6.shop.service.LoaiService;

import java.util.Optional;

@Controller
@RequestMapping("/admin/loai")
public class LoaiAdminController {
@ModelAttribute
public void addUserToModel(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
        model.addAttribute("avatar", user.getHinh());
        model.addAttribute("hoten", user.getHoten());
    }
}
    @Autowired
    private LoaiService loaiService;

    // Danh sách + thêm mới
        @GetMapping
    public String index(Model model) {
        model.addAttribute("loais", loaiService.findAll());
        model.addAttribute("loai", new Loai()); // form thêm mới

        // Thêm biến cho layout
        model.addAttribute("pageTitle", "Quản lý loại sản phẩm");
        model.addAttribute("activePage", "loai");
        model.addAttribute("content", "admin/QLLoai"); // file con

        return "admin/layout"; // trả về layout.html
    }

    // Sửa
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<Loai> opt = loaiService.findById(id);
        if (opt.isEmpty()) return "redirect:/admin/loai?error=notfound";

        model.addAttribute("loais", loaiService.findAll());
        model.addAttribute("loai", opt.get());

        model.addAttribute("pageTitle", "Sửa loại sản phẩm");
        model.addAttribute("activePage", "loai");
        model.addAttribute("content", "admin/QLLoai"); // file con
        model.addAttribute("searchAction", "/admin/loai");
        return "admin/layout";
    }

    // Lưu (thêm hoặc cập nhật)
    @PostMapping("/save")
    public String save(@ModelAttribute("loai") Loai loai, RedirectAttributes ra) {
        loaiService.save(loai);
        ra.addFlashAttribute("message", "Đã lưu danh mục thành công.");
        return "redirect:/admin/loai";
    }

    // Xoá
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        loaiService.delete(id);
        ra.addFlashAttribute("message", "Đã xoá danh mục.");
        return "redirect:/admin/loai";
    }
    
}
