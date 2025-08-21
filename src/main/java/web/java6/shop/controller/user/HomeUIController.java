package web.java6.shop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.java6.shop.service.SanPhamService;

@Controller
@RequestMapping("/")
public class HomeUIController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping
public String index(Model model) {
    model.addAttribute("appleProducts", sanPhamService.getTop5SanPhamMoiNhatByTenLoai(1));
    model.addAttribute("samsungProducts", sanPhamService.getTop5SanPhamMoiNhatByTenLoai(2));
 
    return "user/home";
}

}