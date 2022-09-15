package rePashion.server.domain.statics.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rePashion.server.domain.statics.category.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select distinct c from Category c left join fetch c.children cc left join fetch cc.children where c.categoryId <= 9L")
    List<Category> findTopCategories();
}
