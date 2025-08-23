package web.java6.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.java6.shop.model.DanhGia;
import web.java6.shop.repository.DanhGiaRepository;
import web.java6.shop.service.DanhGiaService;

import java.util.List;

@Service
public class DanhGiaServiceImpl implements DanhGiaService {

    @Autowired
    private DanhGiaRepository danhGiaRepo;

    @Override
    public DanhGia save(DanhGia danhGia) {
        return danhGiaRepo.save(danhGia);
    }

    @Override
    public List<DanhGia> findBySanPham(Integer idSanPham) {
        return danhGiaRepo.findBySanPhamIdSanPham(idSanPham);
    }
}
