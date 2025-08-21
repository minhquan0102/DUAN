package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SANPHAM_VARIANT")
@Data
public class SanPhamVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_variant")
    private Integer idVariant;

    @ManyToOne
    @JoinColumn(name = "id_sanpham")
    private SanPham sanPham;

    @Column(name = "ten_mau")
    private String tenMau;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia_ban")
    private Integer giaBan;
}
