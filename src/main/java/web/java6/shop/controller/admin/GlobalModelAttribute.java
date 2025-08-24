package web.java6.shop.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import web.java6.shop.model.User;

@ControllerAdvice
public class GlobalModelAttribute {
// truyền avt vào tất cả controller để có thể hiển thị
    @ModelAttribute
    public void addUserToModel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("avatar", user.getHinh());
            model.addAttribute("hoten", user.getHoten());
        }
    }
}
