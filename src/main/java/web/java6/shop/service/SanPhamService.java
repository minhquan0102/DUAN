package web.java6.shop.service;

import web.java6.shop.model.SanPham;
import java.util.List;
import java.util.Optional;

public interface SanPhamService {
    List<SanPham> findAll();
    Optional<SanPham> findById(Integer idSanPham);
    SanPham save(SanPham sanPham);
    SanPham update(SanPham sanPham);
    void delete(Integer idSanPham);
    List<SanPham> findByLoai(Integer idLoai);
}
