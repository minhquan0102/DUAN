package web.java6.shop.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.java6.shop.model.SanPham;
import web.java6.shop.model.SanPhamVariant;
import web.java6.shop.service.SanPhamService;
import web.java6.shop.service.SanPhamVariantService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin/variant")
@RequiredArgsConstructor
public class SanPhamVariantController {

    private final SanPhamVariantService variantService;
    private final SanPhamService sanPhamService;

    // Thư mục lưu ảnh (khớp với WebConfig)
    private static final String UPLOAD_DIR = "C:/Users/ASUS/OneDrive/Hình ảnh/images/";

    // Danh sách variant theo sản phẩm
    @GetMapping("/list/{sanPhamId}")
    public String listVariants(@PathVariable("sanPhamId") Integer sanPhamId, Model model) {
        SanPham sanPham = sanPhamService.findById(sanPhamId).orElse(null);
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("variants", variantService.getVariantsBySanPham(sanPhamId));
        model.addAttribute("variant", new SanPhamVariant());
        return "admin/QLSanPhamVariant";
    }

    // Lưu (thêm/sửa)
    @PostMapping("/save")
    public String saveVariant(@ModelAttribute("variant") SanPhamVariant variant,
            @RequestParam("sanPhamId") Integer sanPhamId,
            @RequestParam("file") MultipartFile file) {

        SanPham sanPham = sanPhamService.findById(sanPhamId).orElse(null);
        if (sanPham != null) {
            variant.setSanPham(sanPham);
        }

        // Nếu variant đã có ID → đang edit
        if (variant.getIdVariant() != null) {
            SanPhamVariant oldVariant = variantService.findById(variant.getIdVariant()).orElse(null);

            if (oldVariant != null) {
                // Nếu không chọn ảnh mới → giữ nguyên ảnh cũ
                if (file.isEmpty()) {
                    variant.setHinhAnh(oldVariant.getHinhAnh());
                } else {
                    // Nếu có ảnh mới → xóa ảnh cũ rồi lưu ảnh mới
                    deleteOldImage(oldVariant.getHinhAnh());
                    String newFilePath = saveImage(file);
                    variant.setHinhAnh(newFilePath);
                }
            }
        } else {
            // Thêm mới variant
            if (!file.isEmpty()) {
                String newFilePath = saveImage(file);
                variant.setHinhAnh(newFilePath);
            }
        }

        variantService.save(variant);
        return "redirect:/admin/variant/list/" + sanPhamId;
    }

    // Edit
    @GetMapping("/edit/{sanPhamId}/{id}")
    public String editVariant(@PathVariable("sanPhamId") Integer sanPhamId,
            @PathVariable("id") Integer id,
            Model model) {
        SanPham sanPham = sanPhamService.findById(sanPhamId).orElse(null);
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("variants", variantService.getVariantsBySanPham(sanPhamId));
        model.addAttribute("variant", variantService.findById(id).orElse(new SanPhamVariant()));
        return "admin/QLSanPhamVariant";
    }

    // Delete
    @GetMapping("/delete/{sanPhamId}/{id}")
    public String deleteVariant(@PathVariable("sanPhamId") Integer sanPhamId,
            @PathVariable("id") Integer id) {
        variantService.findById(id).ifPresent(variant -> deleteOldImage(variant.getHinhAnh()));
        variantService.deleteById(id);
        return "redirect:/admin/variant/list/" + sanPhamId;
    }

    // ================== HÀM PHỤ ==================
    private String saveImage(MultipartFile file) {
        try {
            Path path = Paths.get(UPLOAD_DIR);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // tránh trùng tên
            Path filePath = path.resolve(fileName);
            Files.write(filePath, file.getBytes());
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void deleteOldImage(String oldImagePath) {
        if (oldImagePath != null && oldImagePath.startsWith("/images/")) {
            String fileName = oldImagePath.replace("/images/", "");
            Path oldFilePath = Paths.get(UPLOAD_DIR).resolve(fileName);
            try {
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
