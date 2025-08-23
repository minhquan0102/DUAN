package web.java6.shop.controller.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.java6.shop.model.User;
import web.java6.shop.service.UserService;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    // Load danh sách + hiển thị form thêm hoặc sửa
    @GetMapping
public String loadUserPage(@RequestParam(name = "editId", required = false) String editId, Model model) {
    User userForm = new User();

    if (editId != null && !editId.isEmpty()) {
        Optional<User> optionalUser = userService.findById(editId);
        if (optionalUser.isPresent()) {
            userForm = optionalUser.get();
        }
    }

    model.addAttribute("user", userForm); // Dùng cho form thêm/sửa
    model.addAttribute("list", userService.findAll()); // Danh sách người dùng

    // Thêm các biến cho layout
    model.addAttribute("pageTitle", "Quản lý người dùng");
    model.addAttribute("activePage", "users");
    model.addAttribute("content", "admin/QLNguoiDung"); // file con nhúng vào layout

    return "admin/layout"; // luôn trả về layout.html
}
@ModelAttribute
public void addUserToModel(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
        model.addAttribute("avatar", user.getHinh());
        model.addAttribute("hoten", user.getHoten());
    }
}
    // Xử lý thêm mới hoặc cập nhật user
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        boolean isNew = (user.getIdUser() == null || user.getIdUser().isEmpty());

        if (isNew) {
            user.setCreatedAt(LocalDate.now());
            user.setNgaydangky(LocalDate.now());
        }

        user.setUpdatedAt(LocalDate.now());

        userService.save(user);
        return "redirect:/admin/users";
    }

    // Xóa người dùng
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        if (id != null && !id.isEmpty()) {
            userService.deleteById(id);
        }
        return "redirect:/admin/users";
    }
}
