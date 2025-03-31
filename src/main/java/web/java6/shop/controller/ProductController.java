package web.java6.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.java6.shop.model.SanPham;
import web.java6.shop.service.SanPhamService;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/products")
    public String showProducts(Model model) {
        List<SanPham> products = sanPhamService.findAll();
        model.addAttribute("products", products);
        return "products";
    }
}
