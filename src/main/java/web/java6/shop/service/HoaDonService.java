package web.java6.shop.service;

import web.java6.shop.model.HoaDon;
import java.util.List;
import java.util.Optional;

public interface HoaDonService {
    List<HoaDon> findAll();
    Optional<HoaDon> findById(Integer idHoaDon);
    HoaDon save(HoaDon hoaDon);
    HoaDon update(HoaDon hoaDon);
    void delete(Integer idHoaDon);
    // Lấy danh sách đơn hàng theo người dùng
    List<HoaDon> findByUser(String idUser);
}
