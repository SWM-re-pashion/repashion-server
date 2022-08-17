package rePashion.server.domain.statics.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rePashion.server.domain.statics.category.model.GenderCategory;

import java.util.List;

public interface GenderCategoryRepository extends JpaRepository<GenderCategory, Long> {

    @Query("select distinct g from GenderCategory g join fetch g.childrens p join fetch p.childrens")
    List<GenderCategory> findGenderCategories();
}
