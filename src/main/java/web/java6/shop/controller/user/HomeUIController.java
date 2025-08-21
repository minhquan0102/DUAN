package web.java6.shop.controller.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import web.java6.shop.dto.SanPhamKhuyenMaiDTO;
import web.java6.shop.model.User;
import web.java6.shop.service.SanPhamKhuyenMaiService;
import web.java6.shop.service.SanPhamService;
import web.java6.shop.cart.CartService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeUIController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private SanPhamKhuyenMaiService sanPhamKhuyenMaiService;

    @Autowired
    private CartService cartService;  // ✅ Thêm giỏ hàng

    @ModelAttribute
public void addUserToModel(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
        model.addAttribute("avatar", user.getHinh());
        model.addAttribute("hoten", user.getHoten());
    }
}

    @GetMapping
    public String index(Model model, HttpSession session) {
        // Lấy user từ session
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user); // Truyền user ra view

            // ✅ Lấy số lượng sản phẩm trong giỏ
            Long cartCount = cartService.getCartCount(user.getIdUser());
            model.addAttribute("cartCount", cartCount != null ? cartCount : 0);
        } else {
            model.addAttribute("cartCount", 0L);
        }

        // Lấy top 5 sản phẩm theo loại (Apple id=1, Samsung id=2)
        model.addAttribute("appleProducts", sanPhamService.getTop5SanPhamMoiNhatByTenLoai(1));
        model.addAttribute("samsungProducts", sanPhamService.getTop5SanPhamMoiNhatByTenLoai(2));

        // Lấy 5 sản phẩm mới nhất
        model.addAttribute("newProducts", sanPhamService.getTop5SanPhamMoiNhat());

        // Lấy danh sách sản phẩm trong chương trình khuyến mãi
        List<SanPhamKhuyenMaiDTO> flashSaleProducts =
                sanPhamKhuyenMaiService.findTatCaSanPhamKhuyenMai("Khuyến mãi dịp lễ 2/9");
        model.addAttribute("flashSaleProducts", flashSaleProducts);

        // Truyền thời gian kết thúc khuyến mãi (nếu có)
        if (!flashSaleProducts.isEmpty()) {
            SanPhamKhuyenMaiDTO firstProduct = flashSaleProducts.get(0);
            LocalDate ngayKetThuc = firstProduct.getNgayKetThuc();
            LocalTime gioKetThuc = firstProduct.getGioKetThuc();
            if (ngayKetThuc != null && gioKetThuc != null) {
                String endDateTime = ngayKetThuc.atTime(gioKetThuc).toString();
                model.addAttribute("flashSaleEndDateTime", endDateTime);
            } else {
                model.addAttribute("flashSaleEndDateTime", "");
            }
        } else {
            model.addAttribute("flashSaleEndDateTime", "");
        }

        return "user/home";
    }
}
