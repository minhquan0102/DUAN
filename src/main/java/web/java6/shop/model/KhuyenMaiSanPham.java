package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "KHUYENMAI_SANPHAM")
@Data
public class KhuyenMaiSanPham {

     @EmbeddedId
    private KhuyenMaiSanPhamKey id;

    @ManyToOne
    @MapsId("idKhuyenMai")
    @JoinColumn(name = "id_km")
    private KhuyenMai khuyenMai;

    @ManyToOne
    @MapsId("idSanPham")
    @JoinColumn(name = "id_sanpham")
    private SanPham sanPham;


    
}
