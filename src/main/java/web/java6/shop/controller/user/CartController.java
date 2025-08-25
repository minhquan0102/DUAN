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

    // Hiển thị giỏ hàng
    @GetMapping
    public String xemGioHang(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        Cart cart = cartService.getCart(user.getIdUser())
                .orElse(new Cart(user.getIdUser(), new ArrayList<>()));

        model.addAttribute("cart", cart);
        return "user/cart"; // View Thymeleaf
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public String themVaoGio(@RequestParam("idSanPham") Integer idSanPham,
            @RequestParam("variantId") Integer variantId,
            @RequestParam(defaultValue = "1") Integer soLuong,
            @RequestParam(required = false) String mau,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        if (soLuong == null || soLuong <= 0)
            soLuong = 1;

        SanPham sanPham = sanPhamService.findById(idSanPham).orElse(null);
        if (sanPham == null)
            return "redirect:/products";

        SanPhamVariant variant = variantRepo.findById(variantId).orElse(null);
        if (variant == null)
            return "redirect:/products";

        CartItem item = new CartItem();
        item.setIdSanPham(sanPham.getIdSanPham());
        item.setTenSanPham(sanPham.getTenSanPham());

        double giaGoc = variant.getGiaBan() != null ? variant.getGiaBan() : 0;
        int giamGia = sanPham.getGiamgia() != null ? sanPham.getGiamgia() : 0;

        double giaSauGiam;
        if (giamGia <= 100) {
            giaSauGiam = giaGoc - (giaGoc * giamGia / 100.0);
        } else {
            giaSauGiam = giaGoc - giamGia;
        }
        if (giaSauGiam < 0)
            giaSauGiam = 0;

        item.setGia(giaSauGiam);
        item.setSoLuong(soLuong);
        item.setMau(mau != null && !mau.isEmpty() ? mau : "Không chọn");
        item.setAnh(variant.getHinhAnh() != null ? variant.getHinhAnh() : sanPham.getHinh());

        cartService.addItem(user.getIdUser(), item);
        return "redirect:/cart";
    }

    // Xóa 1 sản phẩm khỏi giỏ hàng
    @GetMapping("/delete/{idSanPham}")
    public String xoaKhoiGio(@PathVariable("idSanPham") Integer idSanPham,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

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
        if (user == null)
            return "redirect:/login";

        if (soLuong == null || soLuong <= 0)
            soLuong = 1;

        cartService.updateQuantity(user.getIdUser(), idSanPham, mau, soLuong);
        return "redirect:/cart";
    }

    // Xóa toàn bộ giỏ hàng
    @GetMapping("/delete-all")
    public String xoaToanBoGio(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        cartService.clearCart(user.getIdUser());
        return "redirect:/cart";
    }

    // Thanh toán
    @GetMapping("/checkout")
    public String hienThiTrangThanhToan(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

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
            @RequestParam("address") String address,
            @RequestParam(name = "tongTien", required = false, defaultValue = "0") double tongTien) {

        // Kiểm tra đăng nhập
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Kiểm tra dữ liệu nhập
        if (fullName.trim().isEmpty() || phone.trim().isEmpty() || address.trim().isEmpty()) {
            // Có thể thêm thông báo lỗi qua RedirectAttributes
            return "redirect:/cart?error=missing_info";
        }

        if (tongTien <= 0) {
            // Nếu tổng tiền không hợp lệ
            return "redirect:/cart?error=invalid_total";
        }

        // Thực hiện đặt hàng
        cartService.checkout(user.getIdUser(), fullName, phone, address, tongTien);

        return "redirect:/cart?success=true";
    }

    @GetMapping("/payment")
    public String showPaymentPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        Cart cart = cartService.getCart(user.getIdUser())
                .orElse(new Cart(user.getIdUser(), new ArrayList<>()));

        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getGia() * i.getSoLuong())
                .sum();

        model.addAttribute("total", total);
        model.addAttribute("cart", cart);
        return "payment"; // Tên view payment.html
    }

}
