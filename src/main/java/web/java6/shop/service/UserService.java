package web.java6.shop.service;

import web.java6.shop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(String idUser);
    User save(User user);
    User update(User user);
    void delete(String idUser);
}
