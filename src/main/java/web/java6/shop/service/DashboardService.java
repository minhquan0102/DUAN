package web.java6.shop.service;

import web.java6.shop.model.HoaDon;
import web.java6.shop.repository.HoaDonRepository;
import web.java6.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private HoaDonRepository hoaDonRepo;
    @Autowired
    private UserRepository userRepo;

    public Double getTongDoanhThu() {
        return hoaDonRepo.tongDoanhThu();
    }

    public Long getTongDonHang() {
        return hoaDonRepo.tongDonHang();
    }

    public Long getTongKhachHang() {
        return userRepo.tongKhachHang();
    }

    public List<HoaDon> getDonHangGanDay() {
        return hoaDonRepo.findAll()
                .stream()
                .sorted((a,b) -> b.getNgayTao().compareTo(a.getNgayTao()))
                .limit(5)
                .toList();
    }
}