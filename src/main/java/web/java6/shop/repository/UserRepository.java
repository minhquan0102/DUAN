package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import web.java6.shop.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT COUNT(u) FROM User u WHERE u.vaitro = false")
Long tongKhachHang();
}
