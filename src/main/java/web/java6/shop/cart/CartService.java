package web.java6.shop.cart;

import java.util.Optional;

public interface CartService {
    // Lấy giỏ hàng của người dùng theo idUser
    Optional<Cart> getCart(String idUser);

    // Thêm sản phẩm vào giỏ hàng
    Cart addItem(String idUser, CartItem item);

    // Xoá sản phẩm khỏi giỏ hàng
    Cart removeItem(String idUser, Integer idSanPham);

    // Xoá toàn bộ giỏ hàng của người dùng
    void clearCart(String idUser);
}
