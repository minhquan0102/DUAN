package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.java6.shop.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
