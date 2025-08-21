package web.java6.shop.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SanPhamKhuyenMaiDTO {
    private Integer idSanPham;
    private String tenSanPham;
    private Integer giaGoc;
    private String hinhAnh;

    private Integer idKhuyenMai;
    private String tenKhuyenMai;
    private String moTa;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private Integer phanTram;

    private Double giaSauKhuyenMai;
}
