package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "LOAI")
@Data
public class Loai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loai")
    private Integer idLoai;

    @Column(name = "ten_loai")
    private String tenLoai;
    
    public Integer getId() {
        return this.idLoai;
    }
}
