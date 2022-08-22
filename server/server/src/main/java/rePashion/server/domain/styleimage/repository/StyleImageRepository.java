package rePashion.server.domain.styleimage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.preference.model.StyleImage;

public interface StyleImageRepository extends JpaRepository<StyleImage, Long> {
}
