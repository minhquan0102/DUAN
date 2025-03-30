package web.java6.shop.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class HoaDonChiTietKey implements Serializable {
    private Integer idHoaDon;
    private Integer idSanPham;
}
