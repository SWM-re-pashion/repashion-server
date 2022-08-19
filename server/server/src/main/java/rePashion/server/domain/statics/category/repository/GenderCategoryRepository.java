package rePashion.server.domain.statics.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rePashion.server.domain.statics.category.model.GenderCategory;

import java.util.List;
import java.util.Optional;

public interface GenderCategoryRepository extends JpaRepository<GenderCategory, Long> {

    @Query("select distinct g from GenderCategory g join fetch g.childrens p join fetch p.childrens")
    List<GenderCategory> findGenderCategories();

    @Query("select p.name from GenderCategory p where p.code = :code")
    Optional<String> findGenderCategoryByCode(@Param("code") String code);
}
