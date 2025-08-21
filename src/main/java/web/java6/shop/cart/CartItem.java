package web.java6.shop.cart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {
    private Integer idSanPham;
    private String tenSanPham;
    private Integer soLuong;
    private double gia;
    private String mau;         // MÀU sản phẩm
    private String anh;         // ảnh sản phẩm
}

