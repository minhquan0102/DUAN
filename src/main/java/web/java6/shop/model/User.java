package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    private String idUser;

    private String sdt;
    private String hinh;
    private String hoten;
    private String matkhau;
    private boolean vaitro;
}
