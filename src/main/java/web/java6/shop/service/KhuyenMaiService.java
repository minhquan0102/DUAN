package web.java6.shop.service;

import web.java6.shop.model.KhuyenMai;

import java.util.List;
import java.util.Optional;

public interface KhuyenMaiService {
    List<KhuyenMai> findAll();
    Optional<KhuyenMai> findById(Integer id);
    KhuyenMai save(KhuyenMai km);
    void deleteById(Integer id);
}
