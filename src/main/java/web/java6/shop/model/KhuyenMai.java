package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "KHUYENMAI")
@Data
public class KhuyenMai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_km")
    private Integer idKhuyenMai;

    @Column(name = "ten_km")
    private String tenKhuyenMai;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "ngaybatdau")
    private LocalDate ngayBatDau;

    @Column(name = "ngayketthuc")
    private LocalDate ngayKetThuc;

    @Column(name = "phantram")
    private Integer phanTram;

    @Column(name = "loai_apdung")
    private String loaiApDung;

    @Column(name = "gio_batdau")
    private LocalTime gioBatDau;

    @Column(name = "gio_ketthuc")
    private LocalTime gioKetThuc;
}
