package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "KHUYENMAI_LOAI")
@Data
public class KhuyenMaiLoai {

    @EmbeddedId
    private KhuyenMaiLoaiKey id;

    @ManyToOne
    @MapsId("idKhuyenMai")
    @JoinColumn(name = "id_km")
    private KhuyenMai khuyenMai;

    @ManyToOne
    @MapsId("idLoai")
    @JoinColumn(name = "id_loai")
    private Loai loai;
}
