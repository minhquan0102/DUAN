package web.java6.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import web.java6.shop.model.KhuyenMai;
import web.java6.shop.model.User;
import web.java6.shop.service.KhuyenMaiService;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/admin/khuyenmai")
public class KhuyenMaiAdminController {

    @Autowired
    private KhuyenMaiService khuyenMaiService;

    // Thêm thông tin user vào model
    @ModelAttribute
    public void addUserToModel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("avatar", user.getHinh());
            model.addAttribute("hoten", user.getHoten());
        }
    }

    // Danh sách + thêm mới
    @GetMapping
    public String index(Model model) {
        model.addAttribute("dsKhuyenMai", khuyenMaiService.findAll());
        model.addAttribute("khuyenMai", new KhuyenMai());

        // Biến cho layout
        model.addAttribute("pageTitle", "Quản lý khuyến mãi");
        model.addAttribute("activePage", "khuyenmai");
        model.addAttribute("content", "admin/QLKhuyenMai"); // file con

        return "admin/layout"; // trả về layout.html
    }

    // Sửa
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        KhuyenMai km = khuyenMaiService.findById(id).orElse(null);
        if (km == null) return "redirect:/admin/khuyenmai";

        model.addAttribute("dsKhuyenMai", khuyenMaiService.findAll());
        model.addAttribute("khuyenMai", km);

        model.addAttribute("pageTitle", "Sửa khuyến mãi");
        model.addAttribute("activePage", "khuyenmai");
        model.addAttribute("content", "admin/QLKhuyenMai"); // file con

        return "admin/layout";
    }

    // Lưu
    @PostMapping("/save")
    public String save(@ModelAttribute("khuyenMai") KhuyenMai khuyenMai, RedirectAttributes ra) {
        if (khuyenMai.getGioBatDau() == null) {
            khuyenMai.setGioBatDau(LocalTime.of(0, 0));
        }
        if (khuyenMai.getGioKetThuc() == null) {
            khuyenMai.setGioKetThuc(LocalTime.of(23, 59));
        }

        khuyenMaiService.save(khuyenMai);
        ra.addFlashAttribute("message", "Lưu thành công!");
        return "redirect:/admin/khuyenmai";
    }

    // Xoá
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        khuyenMaiService.deleteById(id);
        ra.addFlashAttribute("message", "Xoá thành công!");
        return "redirect:/admin/khuyenmai";
    }
}

