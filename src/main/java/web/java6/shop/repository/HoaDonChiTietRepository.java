package web.java6.shop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.java6.shop.model.HoaDonChiTiet;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {

    // Lấy tất cả chi tiết hóa đơn theo id hóa đơn
    @Query("SELECT h FROM HoaDonChiTiet h WHERE h.hoaDon.id = :idHoaDon")
    List<HoaDonChiTiet> findByIdHoaDon(@Param("idHoaDon") Integer idHoaDon);
}
