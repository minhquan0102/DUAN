package web.java6.shop.service;

import web.java6.shop.model.HoaDonChiTiet;
import web.java6.shop.model.HoaDonChiTietKey;
import java.util.List;
import java.util.Optional;

public interface HoaDonChiTietService {
    List<HoaDonChiTiet> findAll();
    Optional<HoaDonChiTiet> findById(HoaDonChiTietKey key);
    HoaDonChiTiet save(HoaDonChiTiet hoaDonChiTiet);
    HoaDonChiTiet update(HoaDonChiTiet hoaDonChiTiet);
    void delete(HoaDonChiTietKey key);
    // Tùy chỉnh: lấy các chi tiết đơn hàng theo id hóa đơn
    List<HoaDonChiTiet> findByHoaDon(Integer idHoaDon);
}
