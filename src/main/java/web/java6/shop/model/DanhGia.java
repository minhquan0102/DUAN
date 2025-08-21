package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "DANHGIA")
@Data
public class DanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_danhgia")
    private Integer idDanhGia;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_sanpham")
    private SanPham sanPham;

    @Column(name = "sao")
    private Integer sao;

    @Column(name = "noidung")
    private String noiDung;

    @Column(name = "ngaydanhgia")
    private LocalDateTime ngayDanhGia;
}
