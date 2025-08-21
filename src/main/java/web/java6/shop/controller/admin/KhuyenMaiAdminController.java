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

    @GetMapping
    public String index(Model model) {
        model.addAttribute("dsKhuyenMai", khuyenMaiService.findAll());
        model.addAttribute("khuyenMai", new KhuyenMai());
        return "admin/QLKhuyenMai";
    }
@ModelAttribute
public void addUserToModel(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
        model.addAttribute("avatar", user.getHinh());
        model.addAttribute("hoten", user.getHoten());
    }
}
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        KhuyenMai km = khuyenMaiService.findById(id).orElse(null);
        if (km == null) return "redirect:/admin/khuyenmai";
        model.addAttribute("dsKhuyenMai", khuyenMaiService.findAll());
        model.addAttribute("khuyenMai", km);
        return "admin/QLKhuyenMai";
    }

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

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        khuyenMaiService.deleteById(id);
        ra.addFlashAttribute("message", "Xoá thành công!");
        return "redirect:/admin/khuyenmai";
    }
}
