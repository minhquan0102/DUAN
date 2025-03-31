package web.java6.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.java6.shop.cart.CartService;
import web.java6.shop.cart.CartItem;
import web.java6.shop.model.SanPham;
import web.java6.shop.service.SanPhamService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private SanPhamService sanPhamService;

    @PostMapping("/add")
    public String addToCart(@RequestParam("userId") String userId,
                            @RequestParam("productId") Integer productId,
                            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
        SanPham product = sanPhamService.findById(productId).orElse(null);
        if(product == null) {
            return "redirect:/products?error=ProductNotFound";
        }
        CartItem item = new CartItem(productId, product.getTenSanPham(), quantity, product.getGia());
        cartService.addItem(userId, item);
        return "redirect:/products?success=AddedToCart";
    }
}
