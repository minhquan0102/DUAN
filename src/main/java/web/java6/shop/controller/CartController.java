package web.java6.shop.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.java6.shop.cart.CartService;
import web.java6.shop.cart.Cart;
import org.springframework.ui.Model;
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

    @GetMapping
public String viewCart(@RequestParam("userId") String userId, Model model) {
    model.addAttribute("cart", cartService.getCart(userId).orElse(new Cart(userId, new ArrayList<>())));
    return "cart";
}


    @PostMapping("/add")
    public String addToCart(@RequestParam("userId") String userId,
                            @RequestParam("productId") Integer productId,
                            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
        SanPham product = sanPhamService.findById(productId).orElse(null);
        if (product == null) {
            return "redirect:/products?error=ProductNotFound";
        }
    
        CartItem item = new CartItem(productId, product.getTenSanPham(), quantity, product.getGia());
        Cart cart = cartService.addItem(userId, item);
        
        // Kiểm tra giỏ hàng đã cập nhật đúng chưa
        System.out.println("Cart updated: " + cart);
    
        return "redirect:/products?success=AddedToCart";
    }

    @PostMapping("/remove")
public String removeFromCart(@RequestParam("userId") String userId, 
                             @RequestParam("productId") Integer productId) {
    cartService.removeItem(userId, productId);
    return "redirect:/cart?userId=" + userId;
}

}