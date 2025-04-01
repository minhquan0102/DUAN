package web.java6.shop.cart;

import java.util.Optional;

public interface CartService {
    Optional<Cart> getCart(String idUser);
    Cart addItem(String idUser, CartItem item);
    Cart removeItem(String idUser, Integer idSanPham);
    void clearCart(String idUser);
}
