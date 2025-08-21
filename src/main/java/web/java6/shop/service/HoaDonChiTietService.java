package web.java6.shop.service;

import web.java6.shop.model.HoaDonChiTiet;

import java.util.List;
import java.util.Optional;

public interface HoaDonChiTietService {
    List<HoaDonChiTiet> findAll();

    Optional<HoaDonChiTiet> findById(Integer id);  // sửa dùng Integer

    HoaDonChiTiet save(HoaDonChiTiet hoaDonChiTiet);

    HoaDonChiTiet update(HoaDonChiTiet hoaDonChiTiet);

    void delete(Integer id);  // sửa dùng Integer

 
}
