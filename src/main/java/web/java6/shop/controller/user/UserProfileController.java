// UserProfileController.java
package web.java6.shop.controller.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.java6.shop.model.User;
import web.java6.shop.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserProfileController {

    @Autowired
    private UserService userService;

    // Hiển thị thông tin cá nhân
 @GetMapping("/profile")
public String showProfile(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
        return "redirect:/login";
    }

    String idUser = user.getIdUser();
    Optional<User> userOpt = userService.findById(idUser);
    if (userOpt.isPresent()) {
        model.addAttribute("user", userOpt.get());
        return "user/profile"; // -> templates/user/profile.html
    } else {
        model.addAttribute("error", "Không tìm thấy người dùng.");
        return "redirect:/user/home";
    }
}

@PostMapping("/profile/update")
public String updateProfile(@ModelAttribute("user") User updatedUser,
                            HttpSession session, Model model) {
    User sessionUser = (User) session.getAttribute("user");
    if (sessionUser == null) {
        return "redirect:/login";
    }

    updatedUser.setIdUser(sessionUser.getIdUser()); // Lấy ID từ session
    userService.update(updatedUser);

    // Cập nhật lại session
    session.setAttribute("user", updatedUser);

    model.addAttribute("user", updatedUser);
    model.addAttribute("success", "Cập nhật thành công!");
    return "user/profile";
}
}
