package web.java6.shop.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.ArrayList;
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
    // Kiểm tra nếu giỏ hàng đã tồn tại
    Cart cart = cartRepository.findById(idUser).orElse(new Cart(idUser, new ArrayList<>()));

    // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
    boolean exists = false;
    for (CartItem ci : cart.getItems()) {
        if (ci.getIdSanPham().equals(item.getIdSanPham())) {
            ci.setSoLuong(ci.getSoLuong() + item.getSoLuong());
            exists = true;
            break;
        }
    }
    
    // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
    if (!exists) {
        cart.getItems().add(item);
    }
    
    // Lưu lại giỏ hàng vào Redis
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
