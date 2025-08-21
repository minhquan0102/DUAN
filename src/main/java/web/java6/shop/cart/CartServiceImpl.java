package web.java6.shop.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.java6.shop.model.HoaDon;
import web.java6.shop.model.HoaDonChiTiet;
import web.java6.shop.model.SanPham;
import web.java6.shop.model.User;

import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import web.java6.shop.repository.HoaDonRepository;
import web.java6.shop.repository.HoaDonChiTietRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;  // Sử dụng repository cho HoaDon

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;  // Sử dụng repository cho HoaDonChiTiet

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

   @Override
public void checkout(String idUser, String fullName, String phone, String address) {
    Cart cart = cartRepository.findById(idUser).orElse(new Cart(idUser, new ArrayList<>()));

    if (cart.getItems().isEmpty()) {
        throw new RuntimeException("Giỏ hàng trống. Không thể thanh toán.");
    }

    HoaDon hoaDon = new HoaDon();
    hoaDon.setNgayTao(LocalDate.now());
    hoaDon.setTrangThai("Đặt hàng thành công và đang chờ xử lý");
    hoaDon.setDiaChi(address);
    hoaDon.setGiaoHang("Giao hàng tận nơi");

    User user = new User();
    user.setIdUser(idUser);
    hoaDon.setUser(user);

    HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);

    for (CartItem cartItem : cart.getItems()) {
        if (cartItem.getIdSanPham() == null || cartItem.getSoLuong() <= 0) {
            throw new RuntimeException("Sản phẩm trong giỏ hàng không hợp lệ.");
        }

        HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();

        hoaDonChiTiet.setSoLuong(cartItem.getSoLuong());
        hoaDonChiTiet.setTenSanPham(cartItem.getTenSanPham());
        hoaDonChiTiet.setMauSac(cartItem.getMau());
        hoaDonChiTiet.setGiaBan(cartItem.getGia());

        hoaDonChiTiet.setHoaDon(savedHoaDon);

        hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    clearCart(idUser);
}


@Override
public void updateQuantity(String idUser, Integer idSanPham, String mau, Integer soLuong) {
    Cart cart = cartRepository.findById(idUser).orElse(new Cart(idUser, new ArrayList<>()));
    for (CartItem item : cart.getItems()) {
        if (item.getIdSanPham().equals(idSanPham) && item.getMau().equals(mau)) {
            item.setSoLuong(soLuong);
            break;
        }
    }
    cartRepository.save(cart);
}
} 
