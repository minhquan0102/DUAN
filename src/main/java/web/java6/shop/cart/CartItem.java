package web.java6.shop.cart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Integer idSanPham;
    private String tenSanPham;
    private Integer soLuong;
    private Integer gia;
}
