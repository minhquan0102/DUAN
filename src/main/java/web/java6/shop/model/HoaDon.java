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
    private Integer idHoaDon;

    private LocalDate ngayTao;
    private String trangThai;
    private String diaChi;
    private String giaoHang;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
