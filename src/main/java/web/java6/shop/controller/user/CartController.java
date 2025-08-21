package web.java6.shop.controller.user;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import web.java6.shop.cart.Cart;
import web.java6.shop.cart.CartItem;
import web.java6.shop.cart.CartService;
import web.java6.shop.model.SanPham;
import web.java6.shop.model.User;
import web.java6.shop.service.SanPhamService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private SanPhamService sanPhamService;

    // Hiển thị giỏ hàng
    @GetMapping
    public String xemGioHang(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Cart cart = cartService.getCart(user.getIdUser())
                .orElse(new Cart(user.getIdUser(), new ArrayList<>()));

        model.addAttribute("cart", cart);
        return "user/cart"; // View Thymeleaf
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public String themVaoGio(@RequestParam("idSanPham") Integer idSanPham,
                             @RequestParam(defaultValue = "1") Integer soLuong,
                             @RequestParam(required = false) String mau,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (soLuong == null || soLuong <= 0) soLuong = 1;

        SanPham sanPham = sanPhamService.findById(idSanPham).orElse(null);
        if (sanPham == null) return "redirect:/products";

        CartItem item = new CartItem();
        item.setIdSanPham(sanPham.getIdSanPham());
        item.setTenSanPham(sanPham.getTenSanPham());
        item.setGia(sanPham.getGia());
        item.setSoLuong(soLuong);
        item.setMau((mau != null && !mau.isEmpty()) ? mau : "Không chọn");
        item.setAnh(sanPham.getHinh());

        cartService.addItem(user.getIdUser(), item);
        return "redirect:/cart";
    }

    // Xóa 1 sản phẩm khỏi giỏ hàng
    @GetMapping("/delete/{idSanPham}")
    public String xoaKhoiGio(@PathVariable("idSanPham") Integer idSanPham,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        cartService.removeItem(user.getIdUser(), idSanPham);
        return "redirect:/cart";
    }

    // Cập nhật số lượng sản phẩm
    @PostMapping("/update")
    public String capNhatSoLuong(@RequestParam("idSanPham") Integer idSanPham,
                                 @RequestParam("soLuong") Integer soLuong,
                                 @RequestParam("mau") String mau,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (soLuong == null || soLuong <= 0) soLuong = 1;

        cartService.updateQuantity(user.getIdUser(), idSanPham, mau, soLuong);
        return "redirect:/cart";
    }

    // Xóa toàn bộ giỏ hàng
    @GetMapping("/delete-all")
    public String xoaToanBoGio(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        cartService.clearCart(user.getIdUser());
        return "redirect:/cart";
    }

    // Thanh toán
@GetMapping("/checkout")
public String hienThiTrangThanhToan(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user == null) return "redirect:/login";

    Cart cart = cartService.getCart(user.getIdUser())
            .orElse(new Cart(user.getIdUser(), new ArrayList<>()));

    double total = cart.getItems().stream()
        .mapToDouble(i -> i.getGia() * i.getSoLuong())
        .sum();

    model.addAttribute("cart", cart);
    model.addAttribute("total", total);

    return "user/checkout"; // -> checkout.html
}


@PostMapping("/payment")
public String thanhToan(HttpSession session,
                        @RequestParam("fullName") String fullName,
                        @RequestParam("phone") String phone,
                        @RequestParam("address") String address) {
    User user = (User) session.getAttribute("user");
    if (user == null) return "redirect:/login";

    cartService.checkout(user.getIdUser(), fullName, phone, address);
    return "redirect:/cart";
}

}
