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
        Cart cart = cartRepository.findById(idUser).orElse(new Cart(idUser, new ArrayList<>()));
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }
        boolean itemExists = false;
        for (CartItem ci : cart.getItems()) {
            if (ci.getIdSanPham().equals(item.getIdSanPham())) {
                // Cập nhật số lượng
                ci.setSoLuong(ci.getSoLuong() + item.getSoLuong());
                // Nếu tên sản phẩm chưa được set, cập nhật nó
                if (ci.getTenSanPham() == null || ci.getTenSanPham().trim().isEmpty()) {
                    ci.setTenSanPham(item.getTenSanPham());
                }
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            cart.getItems().add(item);
        }
        return cartRepository.save(cart);
    }
    
    @Override
    public Cart removeItem(String idUser, Integer idSanPham) {
        Cart cart = cartRepository.findById(idUser).orElse(new Cart(idUser, new ArrayList<>()));
        List<CartItem> updatedItems = cart.getItems().stream()
                .filter(ci -> !ci.getIdSanPham().equals(idSanPham))
                .collect(Collectors.toList());
        cart.setItems(updatedItems);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(String idUser) {
        if (cartRepository.existsById(idUser)) {
            cartRepository.deleteById(idUser);
        }
    }
}
