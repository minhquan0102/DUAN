package web.java6.shop.service;

import web.java6.shop.model.DanhGia;
import java.util.List;

public interface DanhGiaService {
    DanhGia save(DanhGia danhGia);
    List<DanhGia> findBySanPham(Integer idSanPham);
}