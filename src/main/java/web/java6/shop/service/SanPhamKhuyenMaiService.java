package web.java6.shop.service;

import web.java6.shop.dto.SanPhamKhuyenMaiDTO;

import java.util.List;

public interface SanPhamKhuyenMaiService {
    List<SanPhamKhuyenMaiDTO> findTatCaSanPhamKhuyenMai(String moTaKhuyenMai);
}
