package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.java6.shop.model.HoaDonChiTiet;
import web.java6.shop.model.HoaDonChiTietKey;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, HoaDonChiTietKey> {
}
