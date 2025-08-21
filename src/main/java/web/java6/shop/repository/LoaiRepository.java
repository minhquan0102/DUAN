package web.java6.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import web.java6.shop.model.Loai;

public interface LoaiRepository extends JpaRepository<Loai, Integer> {
    Optional<Loai> findByTenLoaiIgnoreCase(String tenLoai);
}