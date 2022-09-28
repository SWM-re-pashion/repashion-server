package rePashion.server.domain.statics.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rePashion.server.domain.statics.category.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select distinct c from Category c left join fetch c.children cc left join fetch cc.children where c.categoryId <= 9L")
    List<Category> findTopCategories();

    @Query("select c from Category c where c.categoryId = :id")
    Optional<Category> findCategoryByCategoryId(@Param("id") Long id);
}
