package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "SANPHAM")
@Data
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sanpham")
    private Integer idSanPham;

    @Column(name = "ten_sanpham")
    private String tenSanPham;
    
    @Column(name = "soluong")
    private Integer soLuong;
    
    @Column(name = "hinh")
    private String hinh;
    
    @Column(name = "mota")
    private String mota;
    
    @Column(name = "motangan")
    private String motangan;
    
    @Column(name = "gia")
    private Integer gia;
    
    @Column(name = "giamgia")
    private Integer giamgia;
    
    @Column(name = "ngaytao")
    private LocalDate ngayTao;

    @ManyToOne
    @JoinColumn(name = "id_loai")
    private Loai loai;
}
