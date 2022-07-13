package rePashion.server.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.user.model.ColorEntity;

public interface ColorEntityRepository extends JpaRepository<ColorEntity, Long> {
}
