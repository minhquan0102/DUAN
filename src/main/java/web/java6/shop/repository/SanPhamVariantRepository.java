package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import web.java6.shop.model.SanPhamVariant;

import java.util.List;

public interface SanPhamVariantRepository extends JpaRepository<SanPhamVariant, Integer> {
     List<SanPhamVariant> findBySanPham_IdSanPham(Integer idSanPham);
     
    @Transactional
    void deleteBySanPham_IdSanPham(Integer idSanPham);
}
