package web.java6.shop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "USERS")
@Data
public class User {

    @Id
    @Column(name = "id_user", nullable = false)
    private String idUser; // Email

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

    @Column(name = "gioitinh")
    private String gioitinh;

    @Column(name = "ngaysinh")
    private LocalDate ngaysinh;

    @Column(name = "diachi")
    private String diachi;

    @Column(name = "trangthai")
    // ĐÚNG – dùng wrapper class
    private Boolean trangthai;


    @Column(name = "ngaydangky")
    private LocalDate ngaydangky;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}
