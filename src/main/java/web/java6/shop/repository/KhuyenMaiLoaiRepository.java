package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.java6.shop.model.KhuyenMaiLoai;
import web.java6.shop.model.KhuyenMaiLoaiKey;

import java.util.List;

public interface KhuyenMaiLoaiRepository extends JpaRepository<KhuyenMaiLoai, KhuyenMaiLoaiKey> {
    List<KhuyenMaiLoai> findByLoaiIdLoai(Integer idLoai);
}
