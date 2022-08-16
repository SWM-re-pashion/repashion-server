package rePashion.server.domain.statics.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.statics.category.model.GenderCategory;

public interface GenderCategoryRepository extends JpaRepository<GenderCategory, Long> {
}
