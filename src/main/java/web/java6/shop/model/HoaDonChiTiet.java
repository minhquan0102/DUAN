package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "HOADONCHITIET")
@Data
public class HoaDonChiTiet {
    @EmbeddedId
    private HoaDonChiTietKey id;

    @Column(name = "soluong")
    private Integer soLuong;

    @ManyToOne
    @MapsId("idHoaDon")
    @JoinColumn(name = "id_hoadon")
    private HoaDon hoaDon;

    @ManyToOne
    @MapsId("idSanPham")
    @JoinColumn(name = "id_sanpham")
    private SanPham sanPham;
}
