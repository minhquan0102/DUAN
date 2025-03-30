package web.java6.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.java6.shop.model.SanPham;
import web.java6.shop.repository.SanPhamRepository;
import web.java6.shop.service.SanPhamService;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<SanPham> findAll() {
        return sanPhamRepository.findAll();
    }

    @Override
    public Optional<SanPham> findById(Integer idSanPham) {
        return sanPhamRepository.findById(idSanPham);
    }

    @Override
    public SanPham save(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }

    @Override
    public SanPham update(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }

    @Override
    public void delete(Integer idSanPham) {
        sanPhamRepository.deleteById(idSanPham);
    }

    @Override
    public List<SanPham> findByLoai(Integer idLoai) {
        return sanPhamRepository.findByLoaiIdLoai(idLoai);
    }
}
