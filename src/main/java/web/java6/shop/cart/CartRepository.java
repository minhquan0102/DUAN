package web.java6.shop.cart;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import web.java6.shop.model.HoaDon;

public interface CartRepository extends CrudRepository<Cart, String> {


}
