package rePashion.server.domain.statics.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rePashion.server.domain.statics.category.model.GenderCategory;

import javax.persistence.OrderBy;
import java.util.List;
import java.util.Optional;

public interface GenderCategoryRepository extends JpaRepository<GenderCategory, Long> {

    @Query("select distinct g from GenderCategory g inner join fetch g.childrens p join fetch p.childrens q")
    List<GenderCategory> findAllCategoriesV1();

    @Query("select g from GenderCategory g order by g.id asc")
    List<GenderCategory> findAllGenderCategories();

    @Query("select p.name from GenderCategory p where p.code = :code")
    Optional<String> findGenderCategoryByCode(@Param("code") String code);
}
