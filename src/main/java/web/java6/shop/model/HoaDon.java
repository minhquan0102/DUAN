package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "HOADON")
@Data
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hoadon")
    private Integer idHoaDon;

    @Column(name = "ngaytao")
    private LocalDate ngayTao;

    @Column(name = "trangthai")
    private String trangThai;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "giaohang")
    private String giaoHang;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
