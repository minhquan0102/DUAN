package web.java6.shop.service;

import web.java6.shop.model.ChiTietSanPham;

import java.util.List;
import java.util.Optional;

public interface ChiTietSanPhamService {
    List<ChiTietSanPham> getChiTietBySanPham(Integer idSanPham);

    ChiTietSanPham save(ChiTietSanPham chiTiet);

    void deleteBySanPhamId(Integer idSanPham);

    Optional<ChiTietSanPham> findById(Integer id);

    void deleteById(Integer id);
}
