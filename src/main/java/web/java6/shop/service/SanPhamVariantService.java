package web.java6.shop.service;

import web.java6.shop.model.SanPhamVariant;

import java.util.List;
import java.util.Optional;

public interface SanPhamVariantService {
    List<SanPhamVariant> getVariantsBySanPham(Integer idSanPham);

    Optional<SanPhamVariant> findById(Integer idVariant);

    SanPhamVariant save(SanPhamVariant variant);

    void deleteById(Integer idVariant);

    void deleteBySanPham(Integer idSanPham);
}
