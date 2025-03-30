package web.java6.shop.service;

import web.java6.shop.model.Loai;
import java.util.List;
import java.util.Optional;

public interface LoaiService {
    List<Loai> findAll();
    Optional<Loai> findById(Integer idLoai);
    Loai save(Loai loai);
    Loai update(Loai loai);
    void delete(Integer idLoai);
}
