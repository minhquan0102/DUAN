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
import web.java6.shop.model.SanPhamVariant;
import web.java6.shop.model.User;
import web.java6.shop.repository.SanPhamVariantRepository;
import web.java6.shop.service.SanPhamService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private SanPhamVariantRepository variantRepo;

    @GetMapping
    public String xemGioHang(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        Cart cart = cartService.getCart(user.getIdUser())
                .orElse(new Cart(user.getIdUser(), new ArrayList<>()));

        model.addAttribute("cart", cart);
        return "user/cart";
    }

    @PostMapping("/add")
    public String themVaoGio(@RequestParam("idSanPham") Integer idSanPham,
                             @RequestParam("variantId") Integer variantId,
                             @RequestParam(defaultValue = "1") Integer soLuong,
                             @RequestParam(required = false) String mau,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        if (soLuong == null || soLuong <= 0) soLuong = 1;

        SanPham sanPham = sanPhamService.findById(idSanPham).orElse(null);
        if (sanPham == null) return "redirect:/products";

        SanPhamVariant variant = variantRepo.findById(variantId).orElse(null);
        if (variant == null) return "redirect:/products";

        CartItem item = new CartItem();
        item.setIdSanPham(sanPham.getIdSanPham());
        item.setTenSanPham(sanPham.getTenSanPham());

        long giaGoc = variant.getGiaBan() != null ? variant.getGiaBan() : 0;
        long giamGia = sanPham.getGiamgia() != null ? sanPham.getGiamgia() : 0;

        long giaSauGiam;
        if (giamGia <= 100) {
            giaSauGiam = giaGoc - (giaGoc * giamGia / 100);
        } else {
            giaSauGiam = giaGoc - giamGia;
        }
        if (giaSauGiam < 0) giaSauGiam = 0;

        item.setGia(giaSauGiam);
        item.setSoLuong(soLuong);
        item.setMau(mau != null && !mau.isEmpty() ? mau : "Không chọn");
        item.setAnh(variant.getHinhAnh() != null ? variant.getHinhAnh() : sanPham.getHinh());

        cartService.addItem(user.getIdUser(), item);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{idSanPham}")
    public String xoaKhoiGio(@PathVariable("idSanPham") Integer idSanPham,
                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        cartService.removeItem(user.getIdUser(), idSanPham);
        return "redirect:/cart";
    }

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

    @GetMapping("/delete-all")
    public String xoaToanBoGio(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        cartService.clearCart(user.getIdUser());
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String hienThiTrangThanhToan(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Cart cart = cartService.getCart(user.getIdUser())
                .orElse(new Cart(user.getIdUser(), new ArrayList<>()));

        long total = cart.getItems().stream()
                .mapToLong(i -> i.getGia() * i.getSoLuong())
                .sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "user/checkout";
    }

    @PostMapping("/payment")
    public String thanhToan(HttpSession session,
                            @RequestParam("fullName") String fullName,
                            @RequestParam("phone") String phone,
                            @RequestParam("address") String address,
                            @RequestParam(name = "tongTien", required = false, defaultValue = "0") long tongTien) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (fullName.trim().isEmpty() || phone.trim().isEmpty() || address.trim().isEmpty()) {
            return "redirect:/cart?error=missing_info";
        }

        if (tongTien <= 0) return "redirect:/cart?error=invalid_total";

        cartService.checkout(user.getIdUser(), fullName, phone, address, tongTien);

        return "redirect:/cart?success=true";
    }

    @GetMapping("/payment")
    public String showPaymentPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Cart cart = cartService.getCart(user.getIdUser())
                .orElse(new Cart(user.getIdUser(), new ArrayList<>()));

        long total = cart.getItems().stream()
                .mapToLong(i -> i.getGia() * i.getSoLuong())
                .sum();

        model.addAttribute("total", total);
        model.addAttribute("cart", cart);
        return "payment";
    }
}
