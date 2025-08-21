package web.java6.shop.mapper;

import web.java6.shop.dto.SanPhamDTO;
import web.java6.shop.model.SanPham;

public class SanPhamMapper {

    public static SanPhamDTO toDTO(SanPham sp) {
        SanPhamDTO dto = new SanPhamDTO();
        dto.setIdSanPham(sp.getIdSanPham());
        dto.setTenSanPham(sp.getTenSanPham());
        dto.setSoLuong(sp.getSoLuong());
        dto.setHinh(sp.getHinh());
        dto.setMota(sp.getMota());
        dto.setMotangan(sp.getMotangan());
        dto.setGia(sp.getGia());
        dto.setGiamgia(sp.getGiamgia());
        dto.setNgayTao(sp.getNgayTao());

        if (sp.getLoai() != null) {
            dto.setIdLoai(sp.getLoai().getIdLoai());
            dto.setTenLoai(sp.getLoai().getTenLoai());
        }
       
        // Tính giá sau giảm giá
        int giaSauGiam = sp.getGia();
if (sp.getGiamgia() != null && sp.getGiamgia() > 0) {
    giaSauGiam = sp.getGia() * (100 - sp.getGiamgia()) / 100;
}
dto.setGiaSauGiam(giaSauGiam);
        return dto;
    }
}
