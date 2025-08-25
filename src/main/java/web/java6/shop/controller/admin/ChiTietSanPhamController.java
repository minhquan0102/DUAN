package web.java6.shop.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.java6.shop.model.ChiTietSanPham;
import web.java6.shop.model.SanPham;
import web.java6.shop.service.ChiTietSanPhamService;
import web.java6.shop.service.SanPhamService;

import java.util.List;

@Controller
@RequestMapping("/admin/chitiet")
@RequiredArgsConstructor
public class ChiTietSanPhamController {

    private final ChiTietSanPhamService chiTietSanPhamService;
    private final SanPhamService sanPhamService;

    // ✅ Hiển thị danh sách chi tiết theo sản phẩm
    @GetMapping("/{idSanPham}")
    public String listChiTiet(@PathVariable("idSanPham") Integer idSanPham, Model model) {
        SanPham sanPham = sanPhamService.findById(idSanPham)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + idSanPham));
        List<ChiTietSanPham> chiTiets = chiTietSanPhamService.getChiTietBySanPham(idSanPham);

        model.addAttribute("sanPham", sanPham);
        model.addAttribute("chiTiets", chiTiets);
        model.addAttribute("chiTiet", new ChiTietSanPham()); // để bind form thêm mới
        return "admin/QLChiTietSanPham";
    }

    // ✅ Lưu mới hoặc cập nhật chi tiết
    @PostMapping("/save/{idSanPham}")
    public String saveChiTiet(@PathVariable("idSanPham") Integer idSanPham,
            @ModelAttribute ChiTietSanPham chiTiet) {
        SanPham sanPham = sanPhamService.findById(idSanPham)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + idSanPham));
        chiTiet.setSanPham(sanPham);
        chiTietSanPhamService.save(chiTiet);
        return "redirect:/admin/chitiet/" + idSanPham;
    }

    // ✅ Xóa chi tiết
    @GetMapping("/delete/{idSanPham}/{idChiTiet}")
    public String deleteChiTiet(@PathVariable("idSanPham") Integer idSanPham,
            @PathVariable("idChiTiet") Integer idChiTiet) {
        chiTietSanPhamService.deleteById(idChiTiet);
        return "redirect:/admin/chitiet/" + idSanPham;
    }

    // ✅ Chỉnh sửa chi tiết
    @GetMapping("/edit/{idSanPham}/{idChiTiet}")
    public String editChiTiet(@PathVariable("idSanPham") Integer idSanPham,
            @PathVariable("idChiTiet") Integer idChiTiet,
            Model model) {
        SanPham sanPham = sanPhamService.findById(idSanPham)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + idSanPham));
        ChiTietSanPham chiTiet = chiTietSanPhamService.findById(idChiTiet)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết sản phẩm với ID: " + idChiTiet));

        model.addAttribute("sanPham", sanPham);
        model.addAttribute("chiTiets", chiTietSanPhamService.getChiTietBySanPham(idSanPham));
        model.addAttribute("chiTiet", chiTiet); // binding form sửa
        return "admin/QLChiTietSanPham";
    }
}
