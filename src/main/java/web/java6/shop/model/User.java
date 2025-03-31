package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @Column(name = "id_user")
    private String idUser;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "hinh")
    private String hinh;

    @Column(name = "hoten")
    private String hoten;

    @Column(name = "matkhau")
    private String matkhau;

    @Column(name = "vaitro")
    private boolean vaitro;
}
