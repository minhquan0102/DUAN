package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.java6.shop.model.DanhGia;

import java.util.List;

public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {
    List<DanhGia> findBySanPhamIdSanPham(Integer idSanPham);
}
