package web.java6.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.java6.shop.model.Loai;

public interface LoaiRepository extends JpaRepository<Loai, Integer> {
}
