package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.java6.shop.model.KhuyenMaiSanPham;
import web.java6.shop.model.KhuyenMaiSanPhamKey;

import java.util.List;

public interface KhuyenMaiSanPhamRepository extends JpaRepository<KhuyenMaiSanPham, KhuyenMaiSanPhamKey> {
    List<KhuyenMaiSanPham> findBySanPham_IdSanPham(Integer idSanPham); 
}
