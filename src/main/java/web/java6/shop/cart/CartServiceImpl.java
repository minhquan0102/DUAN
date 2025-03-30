package web.java6.shop.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Optional<Cart> getCart(String idUser) {
        return cartRepository.findById(idUser);
    }

    @Override
    public Cart addItem(String idUser, CartItem item) {
        // Lấy giỏ hàng, nếu chưa có thì tạo mới
        Cart cart = cartRepository.findById(idUser).orElse(new Cart(idUser, null));
        if (cart.getItems() == null) {
            cart.setItems(new java.util.ArrayList<>());
        }
        // Kiểm tra nếu sản phẩm đã tồn tại thì tăng số lượng
        List<CartItem> updatedItems = cart.getItems().stream().map(ci -> {
            if (ci.getIdSanPham().equals(item.getIdSanPham())) {
                ci.setSoLuong(ci.getSoLuong() + item.getSoLuong());
            }
            return ci;
        }).collect(Collectors.toList());
        // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
        boolean exists = cart.getItems().stream().anyMatch(ci -> ci.getIdSanPham().equals(item.getIdSanPham()));
        if (!exists) {
            updatedItems.add(item);
        }
        cart.setItems(updatedItems);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItem(String idUser, Integer idSanPham) {
        Cart cart = cartRepository.findById(idUser).orElse(new Cart(idUser, new java.util.ArrayList<>()));
        if (cart.getItems() != null) {
            List<CartItem> updatedItems = cart.getItems().stream()
                    .filter(ci -> !ci.getIdSanPham().equals(idSanPham))
                    .collect(Collectors.toList());
            cart.setItems(updatedItems);
        }
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(String idUser) {
        cartRepository.deleteById(idUser);
    }
}
