package web.java6.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.java6.shop.model.KhuyenMai;
import web.java6.shop.repository.KhuyenMaiRepository;
import web.java6.shop.service.KhuyenMaiService;

import java.util.List;
import java.util.Optional;

@Service
public class KhuyenMaiServiceImpl implements KhuyenMaiService {

    @Autowired
    private KhuyenMaiRepository kmRepo;

    @Override
    public List<KhuyenMai> findAll() {
        return kmRepo.findAll();
    }

    @Override
    public Optional<KhuyenMai> findById(Integer id) {
        return kmRepo.findById(id);
    }

    @Override
    public KhuyenMai save(KhuyenMai km) {
        return kmRepo.save(km);
    }

    @Override
    public void deleteById(Integer id) {
        kmRepo.deleteById(id);
    }
}
