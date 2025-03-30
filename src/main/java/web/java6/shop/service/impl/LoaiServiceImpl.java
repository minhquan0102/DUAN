package web.java6.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.java6.shop.model.Loai;
import web.java6.shop.repository.LoaiRepository;
import web.java6.shop.service.LoaiService;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiServiceImpl implements LoaiService {

    @Autowired
    private LoaiRepository loaiRepository;

    @Override
    public List<Loai> findAll() {
        return loaiRepository.findAll();
    }

    @Override
    public Optional<Loai> findById(Integer idLoai) {
        return loaiRepository.findById(idLoai);
    }

    @Override
    public Loai save(Loai loai) {
        return loaiRepository.save(loai);
    }

    @Override
    public Loai update(Loai loai) {
        return loaiRepository.save(loai);
    }

    @Override
    public void delete(Integer idLoai) {
        loaiRepository.deleteById(idLoai);
    }
}
