package web.java6.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.java6.shop.model.DanhGia;
import web.java6.shop.repository.DanhGiaRepository;
import web.java6.shop.service.DanhGiaService;

import java.util.List;
import java.util.Optional;

@Service
public class DanhGiaServiceImpl implements DanhGiaService {

    @Autowired
    private DanhGiaRepository repo;

    @Override
    public DanhGia save(DanhGia dg) {
        return repo.save(dg);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<DanhGia> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<DanhGia> findAll() {
        return repo.findAll();
    }

    @Override
    public List<DanhGia> findBySanPhamIdSanPhamAndDaDuyetTrue(Integer idSanPham) {
        return repo.findBySanPhamIdSanPhamAndDaDuyetTrue(idSanPham);
    }

    @Override
    public List<DanhGia> search(String keyword) {
        return repo.findByNoiDungContainingIgnoreCase(keyword);
    }

    @Override
    public void duyet(Integer id) {
        DanhGia dg = repo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));
        dg.setDaDuyet(true);
        repo.save(dg);
    }

    @Override
    public void an(Integer id) {
        DanhGia dg = repo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));
        dg.setDaDuyet(false);
        repo.save(dg);
    }

    @Override
    public void deleteBySanPhamId(Integer idSanPham) {
        repo.deleteBySanPham_IdSanPham(idSanPham);
    }

}