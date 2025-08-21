package web.java6.shop.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class KhuyenMaiSanPhamKey implements Serializable {
    private Integer idKhuyenMai;
    private Integer idSanPham;

    // getters/setters (hoặc dùng Lombok nếu muốn)

    // ✅ Quan trọng: override equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KhuyenMaiSanPhamKey)) return false;
        KhuyenMaiSanPhamKey that = (KhuyenMaiSanPhamKey) o;
        return Objects.equals(idKhuyenMai, that.idKhuyenMai) &&
               Objects.equals(idSanPham, that.idSanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idKhuyenMai, idSanPham);
    }
}
