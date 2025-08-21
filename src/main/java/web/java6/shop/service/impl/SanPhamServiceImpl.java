package web.java6.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.java6.shop.dto.SanPhamDTO;
import web.java6.shop.mapper.SanPhamMapper;
import web.java6.shop.model.KhuyenMai;
import web.java6.shop.model.KhuyenMaiLoai;
import web.java6.shop.model.KhuyenMaiSanPham;
import web.java6.shop.model.Loai;
import web.java6.shop.model.SanPham;
import web.java6.shop.repository.KhuyenMaiLoaiRepository;
import web.java6.shop.repository.KhuyenMaiRepository;
import web.java6.shop.repository.KhuyenMaiSanPhamRepository;
import web.java6.shop.repository.LoaiRepository;
import web.java6.shop.repository.SanPhamRepository;
import web.java6.shop.repository.SanPhamVariantRepository;
import web.java6.shop.service.SanPhamService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private LoaiRepository loaiRepository;

    @Autowired
    private KhuyenMaiRepository khuyenMaiRepository;

    @Autowired
    private KhuyenMaiSanPhamRepository khuyenMaiSanPhamRepository;

    @Autowired
    private KhuyenMaiLoaiRepository khuyenMaiLoaiRepository;
    @Autowired
    private SanPhamVariantRepository sanPhamVariantRepository;

    @Override
    public List<SanPham> findAll() {
        List<SanPham> sanPhams = sanPhamRepository.findAll();
        return applyKhuyenMai(sanPhams);
    }

    @Override
    public Optional<SanPham> findById(Integer idSanPham) {
        Optional<SanPham> sanPham = sanPhamRepository.findById(idSanPham);
        sanPham.ifPresent(sp -> applyKhuyenMai(List.of(sp)));
        return sanPham;
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
        sanPhamVariantRepository.deleteBySanPham_IdSanPham(idSanPham);
        sanPhamRepository.deleteById(idSanPham);
    }

    @Override
    public List<SanPham> findByLoai(Integer idLoai) {
        List<SanPham> sanPhams = sanPhamRepository.findByLoaiIdLoai(idLoai);
        return applyKhuyenMai(sanPhams);
    }

    @Override
    public List<Loai> findAllLoai() {
        return loaiRepository.findAll();
    }

    private List<SanPham> applyKhuyenMai(List<SanPham> sanPhams) {
        LocalDate today = LocalDate.now();
        List<KhuyenMai> activeKhuyenMais = khuyenMaiRepository.findByNgayBatDauLessThanEqualAndNgayKetThucGreaterThanEqual(today, today);

        for (SanPham sp : sanPhams) {
            int maxDiscount = sp.getGiamgia() != null ? sp.getGiamgia() : 0;

            for (KhuyenMai km : activeKhuyenMais) {
                boolean isApplicable = false;

                if ("flashsale".equals(km.getLoaiApDung())) {
                    LocalTime nowTime = LocalTime.now();
                    if (km.getGioBatDau() == null || km.getGioKetThuc() == null ||
                        !nowTime.isBefore(km.getGioBatDau()) && !nowTime.isAfter(km.getGioKetThuc())) {
                        isApplicable = true;
                    }
                } else {
                    isApplicable = true;
                }

                if (isApplicable) {
                    if ("toan_bo".equals(km.getLoaiApDung())) {
                        maxDiscount = Math.max(maxDiscount, km.getPhanTram());
                    } else if ("sanpham".equals(km.getLoaiApDung())) {
                        List<KhuyenMaiSanPham> kmspList = khuyenMaiSanPhamRepository.findBySanPham_IdSanPham(sp.getIdSanPham());
                        if (kmspList.stream().anyMatch(kmsp -> kmsp.getKhuyenMai().getIdKhuyenMai().equals(km.getIdKhuyenMai()))) {
                            maxDiscount = Math.max(maxDiscount, km.getPhanTram());
                        }
                    } else if ("loai".equals(km.getLoaiApDung())) {
                        List<KhuyenMaiLoai> kmlList = khuyenMaiLoaiRepository.findByLoaiIdLoai(sp.getLoai().getIdLoai());
                        if (kmlList.stream().anyMatch(kml -> kml.getKhuyenMai().getIdKhuyenMai().equals(km.getIdKhuyenMai()))) {
                            maxDiscount = Math.max(maxDiscount, km.getPhanTram());
                        }
                    }
                }
            }

            if (maxDiscount > 0) {
                double discountedPrice = sp.getGia() * (1 - maxDiscount / 100.0);
                sp.setGiamgia(maxDiscount);
                sp.setGia((int) Math.round(discountedPrice));
            }
        }
        return sanPhams;
    }

    @Override
public Page<SanPham> searchSanPham(String keyword, Pageable pageable) {
    Page<SanPham> page = sanPhamRepository.findByTenSanPhamContainingIgnoreCase(keyword, pageable);
    applyKhuyenMai(page.getContent()); // áp khuyến mãi
    return page;
}

//home 
 @Override
public List<SanPhamDTO> getTop5SanPhamMoiNhatByTenLoai(Integer idLoai) {
    List<SanPham> sanPhams = sanPhamRepository.findTop5ByLoai_IdOrderByNgayTaoDesc(idLoai);
    
    // 🔴 Thêm dòng này để tính khuyến mãi
    applyKhuyenMai(sanPhams);

    return sanPhams.stream()
                   .map(SanPhamMapper::toDTO)
                   .collect(Collectors.toList());
}

@Override
public List<SanPhamDTO> getTop5SanPhamMoiNhat() {
    // Lấy top 5 sản phẩm mới nhất
    List<SanPham> sanPhams = sanPhamRepository.findTop5ByOrderByNgayTaoDesc();
    
    // Log số lượng sản phẩm để kiểm tra
    System.out.println("Số sản phẩm lấy được: " + sanPhams.size());
    
    // Áp dụng khuyến mãi
    applyKhuyenMai(sanPhams);

    // Ánh xạ sang SanPhamDTO
    List<SanPhamDTO> result = sanPhams.stream()
                                      .map(SanPhamMapper::toDTO)
                                      .collect(Collectors.toList());
    
    // Đảm bảo chỉ trả về tối đa 5 sản phẩm
    if (result.size() > 5) {
        System.out.println("Cảnh báo: Danh sách vượt quá 5 sản phẩm, giới hạn lại.");
        return result.subList(0, 5);
    }
    
    return result;


}}