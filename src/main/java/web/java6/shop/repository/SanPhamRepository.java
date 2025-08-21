package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.java6.shop.model.Loai;
import web.java6.shop.model.SanPham;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    List<SanPham> findByLoaiIdLoai(Integer idLoai);
    Page<SanPham> findByTenSanPhamContainingIgnoreCase(String keyword, Pageable pageable);
//lấy 5 sản phẩm mới nhất theo loại
  List<SanPham> findTop5ByLoai_IdOrderByNgayTaoDesc(Integer idLoai);
}
