package web.java6.shop.cart;

import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder.In;

public interface CartService {
    Optional<Cart> getCart(String idUser);
    Cart addItem(String idUser, CartItem item);
    Cart removeItem(String idUser, Integer idSanPham);
    void clearCart(String idUser);
    void checkout(String idUser, String fullName, String phone, String address,double tongTien);
    void updateQuantity(String idUser, Integer idSanPham, String mau, Integer soLuong); 
    Long getCartCount(String userId);
}
