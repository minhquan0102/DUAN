package web.java6.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import web.java6.shop.model.SanPham;
import web.java6.shop.model.SanPhamVariant;
import web.java6.shop.model.User;
import web.java6.shop.model.Loai;
import web.java6.shop.service.SanPhamService;
import web.java6.shop.repository.SanPhamVariantRepository;

import java.beans.PropertyEditorSupport;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/admin/sanpham")
public class SanPhamAdminController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private SanPhamVariantRepository variantRepo;

    // -------- DANH SÁCH + TÌM KIẾM + PHÂN TRANG -------- //
    @GetMapping
    public String listSanPham(Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @RequestParam(value = "keyword", required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SanPham> sanPhamPage = sanPhamService.searchSanPham(keyword == null ? "" : keyword, pageable);

        model.addAttribute("sanPhamPage", sanPhamPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("loais", sanPhamService.findAllLoai());
        model.addAttribute("sanPham", new SanPham());
        model.addAttribute("variants", generateEmptyVariants(5));

        // --- layout chung ---
        model.addAttribute("content", "admin/QLSanPham");
        model.addAttribute("pageTitle", "Quản lý sản phẩm");

        return "admin/layout";
    }

    // -------- HIỂN THỊ FORM SỬA -------- //
    @GetMapping("/edit/{id}")
    public String editSanPhamForm(@PathVariable("id") Integer id,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  Model model) {

        Optional<SanPham> optSanPham = sanPhamService.findById(id);
        if (optSanPham.isEmpty()) {
            return "redirect:/admin/sanpham?error=notfound";
        }

        SanPham sanPham = optSanPham.get();
        List<SanPhamVariant> variants = variantRepo.findBySanPham_IdSanPham(id);
        if (variants.size() < 5) {
            variants.addAll(generateEmptyVariants(5 - variants.size()));
        }

        Pageable pageable = PageRequest.of(page, 5);
        Page<SanPham> sanPhamPage = sanPhamService.searchSanPham(keyword == null ? "" : keyword, pageable);

        model.addAttribute("sanPhamPage", sanPhamPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("loais", sanPhamService.findAllLoai());
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("variants", variants);

        // --- layout chung ---
        model.addAttribute("content", "admin/QLSanPham");
        model.addAttribute("pageTitle", "Chỉnh sửa sản phẩm");

        return "admin/layout";
    }

    // -------- LƯU (THÊM/SỬA) -------- //
    @PostMapping("/save")
    public String saveSanPham(@ModelAttribute("sanPham") SanPham sanPham,
                              @RequestParam(value = "variantColors", required = false) List<String> colors,
                              @RequestParam(value = "variantPrices", required = false) List<Integer> prices,
                              @RequestParam(value = "variantQuantities", required = false) List<Integer> quantities,
                              @RequestParam(value = "variantFiles", required = false) List<MultipartFile> variantFiles,
                              @RequestParam(value = "file", required = false) MultipartFile file,
                              RedirectAttributes ra) {
        // ... (giữ nguyên logic lưu sản phẩm)
        return "redirect:/admin/sanpham";
    }

    // -------- XOÁ -------- //
    @GetMapping("/delete/{id}")
    public String deleteSanPham(@PathVariable("id") Integer id, RedirectAttributes ra) {
        sanPhamService.delete(id);
        ra.addFlashAttribute("message", "Đã xoá sản phẩm.");
        return "redirect:/admin/sanpham";
    }

    // -------- GỢI Ý: Khởi tạo 5 variant trống -------- //
    private List<SanPhamVariant> generateEmptyVariants(int count) {
        List<SanPhamVariant> variants = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            variants.add(new SanPhamVariant());
        }
        return variants;
    }

    // -------- INIT BINDER: Map id -> Loai object -------- //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Loai.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                try {
                    Integer id = Integer.parseInt(text);
                    Loai loai = sanPhamService.findAllLoai().stream()
                            .filter(l -> l.getIdLoai().equals(id))
                            .findFirst()
                            .orElse(null);
                    setValue(loai);
                } catch (Exception e) {
                    setValue(null);
                }
            }
        });
    }

    // -------- TRUYỀN THÔNG TIN USER CHUNG CHO LAYOUT -------- //
    @ModelAttribute
    public void addUserToModel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("avatar", user.getHinh());
            model.addAttribute("hoten", user.getHoten());
        }
        model.addAttribute("loais", sanPhamService.findAllLoai()); // danh mục cũng có thể truyền chung
    }
}
