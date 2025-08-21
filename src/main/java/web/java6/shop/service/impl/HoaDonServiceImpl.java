package web.java6.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.java6.shop.model.HoaDon;
import web.java6.shop.repository.HoaDonRepository;
import web.java6.shop.service.HoaDonService;

import java.util.List;
import java.util.Optional;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public List<HoaDon> findAll() {
        return hoaDonRepository.findAll();
    }

    @Override
    public Optional<HoaDon> findById(Integer idHoaDon) {
        return hoaDonRepository.findById(idHoaDon);
    }

    @Override
    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public HoaDon update(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public void delete(Integer idHoaDon) {
        hoaDonRepository.deleteById(idHoaDon);
    }

    @Override
    public List<HoaDon> findByUser(String idUser) {
        return hoaDonRepository.findByUserIdUser(idUser);
    }
    // 🔴 Hủy đơn: chỉ set active = false
    @Override
    public void cancelOrder(Integer idHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        hoaDon.setActive(true);
        hoaDon.setTrangThai("Đã hủy"); // Cập nhật trạng thái
        hoaDonRepository.save(hoaDon);
    }
    @Override
    public void updateOrderStatus(Integer idHoaDon, String status) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        hoaDon.setTrangThai(status); // Cập nhật trạng thái
        hoaDon.setActive(true); // Đảm bảo đơn hàng vẫn còn hoạt động
        hoaDonRepository.save(hoaDon);
    }
}
