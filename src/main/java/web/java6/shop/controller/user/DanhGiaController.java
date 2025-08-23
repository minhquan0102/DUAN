package web.java6.shop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import web.java6.shop.model.DanhGia;
import web.java6.shop.model.SanPham;
import web.java6.shop.model.User;
import web.java6.shop.service.DanhGiaService;
import web.java6.shop.service.SanPhamService;
import web.java6.shop.service.UserService;
import java.time.LocalDateTime;

@Controller
public class DanhGiaController {

    @Autowired
    private DanhGiaService danhGiaService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private UserService userService;

    @PostMapping("/danhgia/them")
public String themDanhGia(@RequestParam("idSanPham") Integer idSanPham,
                          @RequestParam("sao") Integer sao,
                          @RequestParam("noiDung") String noiDung,
                          HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
        return "redirect:/login";
    }

    SanPham sanPham = sanPhamService.findById(idSanPham)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

    DanhGia danhGia = new DanhGia();
    danhGia.setUser(user);
    danhGia.setSanPham(sanPham);
    danhGia.setSao(sao);
    danhGia.setNoiDung(noiDung);
    danhGia.setNgayDanhGia(LocalDateTime.now());

    danhGiaService.save(danhGia);

    return "redirect:/product/" + idSanPham;
}
}
