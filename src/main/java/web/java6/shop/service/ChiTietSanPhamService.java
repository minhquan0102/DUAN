package web.java6.shop.service;

import web.java6.shop.model.ChiTietSanPham;

import java.util.List;
import java.util.Optional;

public interface ChiTietSanPhamService {
    List<ChiTietSanPham> getChiTietBySanPham(Integer idSanPham);

    ChiTietSanPham save(ChiTietSanPham chiTiet);

    void deleteById(Integer id);

    Optional<ChiTietSanPham> findById(Integer id);
}
