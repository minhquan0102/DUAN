package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.java6.shop.model.KhuyenMai;

import java.time.LocalDate;
import java.util.List;

public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Integer> {
    List<KhuyenMai> findByNgayBatDauLessThanEqualAndNgayKetThucGreaterThanEqual(LocalDate startDate, LocalDate endDate);
}