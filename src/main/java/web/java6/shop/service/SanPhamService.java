package web.java6.shop.service;

import web.java6.shop.dto.SanPhamDTO;
import web.java6.shop.model.Loai;
import web.java6.shop.model.SanPham;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SanPhamService {
    List<SanPham> findAll();
    Optional<SanPham> findById(Integer idSanPham);
    SanPham save(SanPham sanPham);
    SanPham update(SanPham sanPham);
    void delete(Integer idSanPham);
    List<SanPham> findByLoai(Integer idLoai);
    List<Loai> findAllLoai(); // Thêm phương thức này
     Page<SanPham> searchSanPham(String keyword, Pageable pageable);

     //lấy 5 sản phẩm mới nhất theo loại
    List<SanPhamDTO> getTop5SanPhamMoiNhatByTenLoai(Integer idLoai);
    // Lấy 5 sản phẩm mới nhất
    List<SanPhamDTO> getTop5SanPhamMoiNhat();
}