package web.java6.shop.cart;

import org.springframework.data.redis.core.RedisHash;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Cart")
public class Cart implements Serializable { // Cần implements Serializable
    @Id
    private String idUser;
    private List<CartItem> items = new ArrayList<>();

}
