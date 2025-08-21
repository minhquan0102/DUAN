package web.java6.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.java6.shop.dto.SanPhamKhuyenMaiDTO;
import web.java6.shop.service.SanPhamKhuyenMaiService;
import web.java6.shop.service.SanPhamService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/")
public class indexController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private SanPhamKhuyenMaiService sanPhamKhuyenMaiService;

    @GetMapping
    public String index(Model model) {
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
                // Kết hợp ngày và giờ thành chuỗi ISO để sử dụng trong JavaScript
                String endDateTime = ngayKetThuc.atTime(gioKetThuc).toString();
                model.addAttribute("flashSaleEndDateTime", endDateTime);
            } else {
                model.addAttribute("flashSaleEndDateTime", "");
            }
        } else {
            model.addAttribute("flashSaleEndDateTime", "");
        }

        return "index";
    }
}  

