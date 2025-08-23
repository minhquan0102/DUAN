package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.java6.shop.model.DanhGia;

import java.util.List;

public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {

    // Lấy danh sách đánh giá theo id sản phẩm
    List<DanhGia> findBySanPhamIdSanPhamAndDaDuyetTrue(Integer idSanPham);

    List<DanhGia> findByNoiDungContainingIgnoreCase(String keyword);
    // Tính trung bình sao
    @Query("SELECT AVG(d.sao) FROM DanhGia d WHERE d.sanPham.idSanPham = :idSanPham")
    Double findAverageSaoBySanPhamId(Integer idSanPham);
}
