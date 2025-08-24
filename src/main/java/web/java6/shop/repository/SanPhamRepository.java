package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import web.java6.shop.dto.SanPhamKhuyenMaiDTO;
import web.java6.shop.model.Loai;
import web.java6.shop.model.SanPham;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    List<SanPham> findByLoaiIdLoai(Integer idLoai);
    Page<SanPham> findByTenSanPhamContainingIgnoreCase(String keyword, Pageable pageable);
//lấy 5 sản phẩm mới nhất theo loại
  List<SanPham> findTop5ByLoai_IdOrderByNgayTaoDesc(Integer idLoai);
// lấy 5 sản phẩm mới nhất
@Query("SELECT sp FROM SanPham sp ORDER BY sp.ngayTao DESC")
    List<SanPham> findTop5ByOrderByNgayTaoDesc(Pageable pageable);

    default List<SanPham> findTop5ByOrderByNgayTaoDesc() {
        return findTop5ByOrderByNgayTaoDesc(PageRequest.of(0, 5));
    }
//sản phẩm sắp hết
@Query(value = "SELECT TOP (100) * FROM SANPHAM WHERE soluong < 5 ORDER BY soluong ASC, ngaytao DESC", nativeQuery = true)
    List<SanPham> findSanPhamSapHet();
}
