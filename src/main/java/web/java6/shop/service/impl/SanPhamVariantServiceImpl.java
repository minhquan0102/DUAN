package web.java6.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.java6.shop.model.SanPhamVariant;
import web.java6.shop.repository.SanPhamVariantRepository;
import web.java6.shop.service.SanPhamVariantService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamVariantServiceImpl implements SanPhamVariantService {

    private final SanPhamVariantRepository variantRepository;

    @Override
    public List<SanPhamVariant> getVariantsBySanPham(Integer idSanPham) {
        return variantRepository.findBySanPham_IdSanPham(idSanPham);
    }

    @Override
    public Optional<SanPhamVariant> findById(Integer idVariant) {
        return variantRepository.findById(idVariant);
    }

    @Override
    public SanPhamVariant save(SanPhamVariant variant) {
        return variantRepository.save(variant);
    }

    @Override
    public void deleteById(Integer idVariant) {
        variantRepository.deleteById(idVariant);
    }

    @Override
    public void deleteBySanPham(Integer idSanPham) {
        variantRepository.deleteBySanPham_IdSanPham(idSanPham);
    }
}
