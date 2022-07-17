package rePashion.server.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.user.model.StyleImage;

public interface StyleImageRepository extends JpaRepository<StyleImage, Long> {
}
