package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CHITIET_SANPHAM")
@Data
public class ChiTietSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_sanpham")
    private SanPham sanPham;

    @Column(name = "ten_thuoctinh")
    private String tenThuocTinh;

    @Column(name = "gia_tri")
    private String giaTri;
}
