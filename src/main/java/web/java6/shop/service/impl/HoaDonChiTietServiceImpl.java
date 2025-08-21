package web.java6.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.java6.shop.model.HoaDonChiTiet;
import web.java6.shop.repository.HoaDonChiTietRepository;
import web.java6.shop.service.HoaDonChiTietService;

import java.util.List;
import java.util.Optional;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Override
    public List<HoaDonChiTiet> findAll() {
        return hoaDonChiTietRepository.findAll();
    }

    @Override
    public Optional<HoaDonChiTiet> findById(Integer id) {
        return hoaDonChiTietRepository.findById(id);
    }

    @Override
    public HoaDonChiTiet save(HoaDonChiTiet hoaDonChiTiet) {
        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    @Override
    public HoaDonChiTiet update(HoaDonChiTiet hoaDonChiTiet) {
        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    @Override
    public void delete(Integer id) {
        hoaDonChiTietRepository.deleteById(id);
    }


}
