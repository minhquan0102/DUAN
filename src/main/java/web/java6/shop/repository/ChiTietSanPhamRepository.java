package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import web.java6.shop.model.ChiTietSanPham;

import java.util.List;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {

    // Lấy danh sách thông số theo id sản phẩm
    List<ChiTietSanPham> findBySanPham_IdSanPham(Integer idSanPham);

    @Transactional
    void deleteBySanPham_IdSanPham(Integer idSanPham);
}
