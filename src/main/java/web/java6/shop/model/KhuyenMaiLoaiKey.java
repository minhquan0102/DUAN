// file: KhuyenMaiLoaiKey.java
package web.java6.shop.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class KhuyenMaiLoaiKey implements Serializable {
    private Integer idKhuyenMai;
    private Integer idLoai;
}
