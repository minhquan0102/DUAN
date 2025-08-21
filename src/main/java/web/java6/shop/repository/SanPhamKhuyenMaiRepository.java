package web.java6.shop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.java6.shop.model.KhuyenMaiSanPham;
import web.java6.shop.model.KhuyenMaiSanPhamKey;

import java.util.List;

@Repository
public interface SanPhamKhuyenMaiRepository extends CrudRepository<KhuyenMaiSanPham, KhuyenMaiSanPhamKey> {

    @Query(value = """
            SELECT sp.id_sanpham, sp.ten_sanpham, sp.gia, sp.hinh,
                   km.id_km, km.ten_km, km.mota, km.ngaybatdau, km.ngayketthuc, km.gio_batdau, km.gio_ketthuc, km.phantram,
                   (sp.gia - (sp.gia * km.phantram / 100.0)) AS gia_sau_km
            FROM SANPHAM sp
            JOIN KHUYENMAI_SANPHAM ksp ON sp.id_sanpham = ksp.id_sanpham
            JOIN KHUYENMAI km ON km.id_km = ksp.id_km
            WHERE km.mota = :moTa
            """, nativeQuery = true)
    List<Object[]> findSanPhamKhuyenMaiRaw(String moTa);
}
