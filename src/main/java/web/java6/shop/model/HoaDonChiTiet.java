package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "HOADONCHITIET")
@Data
public class HoaDonChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Khóa chính tự tăng

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "ten_sanpham")
    private String tenSanPham;

    @Column(name = "mau_sac")
    private String mauSac;

    @Column(name = "gia_ban")
    private Double giaBan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoadon")
    private HoaDon hoaDon;  // Liên kết khóa ngoại id_hoadon
}
