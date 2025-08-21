package web.java6.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.java6.shop.dto.SanPhamKhuyenMaiDTO;
import web.java6.shop.repository.SanPhamKhuyenMaiRepository;
import web.java6.shop.service.SanPhamKhuyenMaiService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SanPhamKhuyenMaiServiceImpl implements SanPhamKhuyenMaiService {

    @Autowired
    private SanPhamKhuyenMaiRepository repo;
@Override
public List<SanPhamKhuyenMaiDTO> findTatCaSanPhamKhuyenMai(String moTaKhuyenMai) {
    List<Object[]> rows = repo.findSanPhamKhuyenMaiRaw(moTaKhuyenMai);
    return rows.stream().map(r -> {
        SanPhamKhuyenMaiDTO dto = new SanPhamKhuyenMaiDTO();
        dto.setIdSanPham(((Number) r[0]).intValue());
        dto.setTenSanPham((String) r[1]);
        dto.setGiaGoc(((Number) r[2]).intValue());
        dto.setHinhAnh((String) r[3]);

        dto.setIdKhuyenMai(((Number) r[4]).intValue());
        dto.setTenKhuyenMai((String) r[5]);
        dto.setMoTa((String) r[6]);
        // Sửa lỗi: Xử lý Timestamp thay vì Date
        dto.setNgayBatDau(r[7] != null ? ((java.sql.Timestamp) r[7]).toLocalDateTime().toLocalDate() : null);
        dto.setNgayKetThuc(r[8] != null ? ((java.sql.Timestamp) r[8]).toLocalDateTime().toLocalDate() : null);
        dto.setGioBatDau(r[9] != null ? ((Time) r[9]).toLocalTime() : null);
        dto.setGioKetThuc(r[10] != null ? ((Time) r[10]).toLocalTime() : null);
        dto.setPhanTram(((Number) r[11]).intValue());
        dto.setGiaSauKhuyenMai(((Number) r[12]).doubleValue());

        return dto;
    }).collect(Collectors.toList());
}
}