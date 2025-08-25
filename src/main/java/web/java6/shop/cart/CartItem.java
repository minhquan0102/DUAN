package web.java6.shop.cart;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {
    private Integer idSanPham;
    private String tenSanPham;
    private String mau;
    private Long gia; // dùng Long
    private Integer soLuong;
    private String anh;
}
