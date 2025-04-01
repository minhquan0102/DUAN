package web.java6.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.java6.shop.cart.CartService;
import web.java6.shop.cart.CartItem;
import web.java6.shop.cart.Cart;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String viewCart(@RequestParam("userId") String userId, Model model) {
        Optional<Cart> cart = cartService.getCart(userId);
        model.addAttribute("cart", cart.orElse(new Cart(userId, new ArrayList<>())));
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("userId") String userId,
                            @RequestParam("productId") Integer productId,
                            @RequestParam("productName") String productName,
                            @RequestParam("quantity") Integer quantity,
                            @RequestParam("price") Integer price) {
        System.out.println("Received productName: " + productName);
        CartItem item = new CartItem(productId, productName, quantity, price);
        cartService.addItem(userId, item);
        return "redirect:/cart?userId=" + userId;
    }
    

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("userId") String userId,
                                 @RequestParam("productId") Integer productId) {
        cartService.removeItem(userId, productId);
        return "redirect:/cart?userId=" + userId;
    }

    @PostMapping("/clear")
    public String clearCart(@RequestParam("userId") String userId) {
        cartService.clearCart(userId);
        return "redirect:/cart?userId=" + userId;
    }
}
