package web.java6.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import web.java6.shop.model.DanhGia;
import web.java6.shop.model.SanPham;
import web.java6.shop.model.SanPhamVariant;
import web.java6.shop.repository.DanhGiaRepository;
import web.java6.shop.repository.SanPhamVariantRepository;
import web.java6.shop.service.DanhGiaService;
import web.java6.shop.service.LoaiService;
import web.java6.shop.service.SanPhamService;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private SanPhamVariantRepository variantRepo;

    @Autowired
    private LoaiService loaiService; 
    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/products")
    public String showProducts(Model model, @RequestParam(value = "loai", required = false) Integer idLoai) {
        // Lấy danh sách danh mục
        model.addAttribute("loais", loaiService.findAll());

        // Lấy danh sách sản phẩm (theo danh mục nếu có)
        List<SanPham> products;
        if (idLoai != null) {
            products = sanPhamService.findByLoai(idLoai);
        } else {
            products = sanPhamService.findAll();
        }
        model.addAttribute("products", products);

        return "products";
    }

      @Autowired
private DanhGiaService danhGiaService;

@Autowired
private DanhGiaRepository danhGiaRepo;

@GetMapping("/product/{id}")
public String productDetail(@PathVariable("id") Integer id, Model model) {
    SanPham sanPham = sanPhamService.findById(id).orElse(null);
    if (sanPham == null) {
        return "redirect:/products";
    }

    List<SanPhamVariant> variants = variantRepo.findBySanPham_IdSanPham(id);
    List<DanhGia> danhGias = danhGiaRepo.findBySanPhamIdSanPham(id);

    model.addAttribute("product", sanPham);
    model.addAttribute("variants", variants);
    model.addAttribute("danhgias", danhGias); // phải trùng với template

    return "product-detail";
}

}