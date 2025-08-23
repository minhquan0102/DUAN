package web.java6.shop.service;

import web.java6.shop.model.DanhGia;

import java.util.List;
import java.util.Optional;

public interface DanhGiaService {
    DanhGia save(DanhGia dg);
    void delete(Integer id);
    Optional<DanhGia> findById(Integer id);
    List<DanhGia> findAll();
    List<DanhGia> findBySanPhamIdSanPhamAndDaDuyetTrue(Integer idSanPham);
    List<DanhGia> search(String keyword);
    void duyet(Integer id);
    void an(Integer id);
}
