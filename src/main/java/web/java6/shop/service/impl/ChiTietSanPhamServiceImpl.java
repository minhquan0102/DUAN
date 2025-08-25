package web.java6.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.java6.shop.model.ChiTietSanPham;
import web.java6.shop.repository.ChiTietSanPhamRepository;
import web.java6.shop.service.ChiTietSanPhamService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {

    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public Optional<ChiTietSanPham> findById(Integer id) {
        return chiTietSanPhamRepository.findById(id); // ✅ dùng repo
    }

    @Override
    public List<ChiTietSanPham> getChiTietBySanPham(Integer idSanPham) {
        return chiTietSanPhamRepository.findBySanPham_IdSanPham(idSanPham);
    }

    @Override
    public ChiTietSanPham save(ChiTietSanPham chiTiet) {
        return chiTietSanPhamRepository.save(chiTiet);
    }

    @Override
    public void deleteById(Integer id) {
        chiTietSanPhamRepository.deleteById(id);
    }
}
