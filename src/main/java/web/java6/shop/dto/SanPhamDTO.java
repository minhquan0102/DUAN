package web.java6.shop.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SanPhamDTO {
    private Integer idSanPham;
    private String tenSanPham;
    private Integer soLuong;
    private String hinh;
    private String mota;
    private String motangan;
    private Integer gia;
    private Integer giamgia;
    private LocalDate ngayTao;
    private Integer idLoai;
    private String tenLoai;
    private Integer giaSauGiam;

}
