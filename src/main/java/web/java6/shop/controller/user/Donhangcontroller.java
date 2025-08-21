package web.java6.shop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import web.java6.shop.model.HoaDon;
import web.java6.shop.model.User;
import web.java6.shop.service.HoaDonService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class Donhangcontroller {

    @Autowired 
    private HoaDonService hoaDonService;

    @GetMapping("/donhang")
    public String showDonHang(Model model, HttpSession session) {
        // Lấy user từ session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // Nếu chưa login thì về trang login
            return "redirect:/login";
        }

        // Dùng idUser của user từ session để lấy danh sách hóa đơn
        List<HoaDon> dsHoaDon = hoaDonService.findByUser(user.getIdUser());

        // Đưa danh sách đơn hàng ra view
        model.addAttribute("donhangList", dsHoaDon);

        return "user/donhang";
    }
    // Hủy đơn hàng
    @PostMapping("/huydon/{id}")
    public String cancelOrder(@PathVariable("id") Integer id) {
        hoaDonService.cancelOrder(id); // gọi service xử lý hủy
        return "redirect:/user/donhang"; // quay lại danh sách đơn hàng
    }

}
