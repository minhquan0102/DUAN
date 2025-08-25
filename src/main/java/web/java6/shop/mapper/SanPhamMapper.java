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
        int discount = sp.getGiamgia() != null ? sp.getGiamgia() : 0;

        int gia = sp.getGia() != null ? sp.getGia() : 0;

        int giaSauGiam;

        // Nếu discount <= 100 thì coi là %, còn >100 thì coi là số tiền giảm
        if (discount <= 100) {
            giaSauGiam = gia - (gia * discount / 100);
        } else {
            giaSauGiam = gia - discount;
        }

        // Tránh âm
        if (giaSauGiam < 0) {
            giaSauGiam = 0;
        }

        dto.setGiaSauGiam(giaSauGiam);
        System.out.println("SanPham: " + sp.getTenSanPham() +
                " | Gia goc: " + sp.getGia() +
                " | Giam gia raw: " + sp.getGiamgia());

        return dto;
    }
}
