package rePashion.server.domain.preference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.preference.model.StyleImage;

public interface StyleImageRepository extends JpaRepository<StyleImage, Long> {
}
