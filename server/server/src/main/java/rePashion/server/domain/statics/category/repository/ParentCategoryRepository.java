package rePashion.server.domain.statics.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rePashion.server.domain.statics.category.model.ParentCategory;

import java.util.Optional;

public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Long> {

    @Query("select distinct p.name from ParentCategory p where p.code = :code")
    Optional<String> findParentCategoryByCode(@Param("code") String code);
}
