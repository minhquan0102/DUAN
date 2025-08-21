package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import web.java6.shop.model.HoaDon;

import java.time.LocalDate;
import java.util.List;


public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    List<HoaDon> findByUserIdUser(String idUser);

@Query("SELECT COALESCE(SUM(h.tongTien), 0) FROM HoaDon h")
double tongDoanhThu();

    @Query("SELECT COUNT(h) FROM HoaDon h")
    long tongDonHang();

}
