package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "tongtien")
    private double tongTien;

    @Column(name = "active")
    private boolean active = false;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
private List<HoaDonChiTiet> hoaDonChiTiets;

}
