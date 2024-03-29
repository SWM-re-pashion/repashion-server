package rePashion.server.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import rePashion.server.domain.product.model.Product;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {

    @Query("select p from Product p join fetch p.advanceInfo where p.id =:id")
    Optional<Product> findProductEntityGraph(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Product p set p.basicInfo.status = true where p.id = :id")
    void updateStatus(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Product p set p.basicInfo.visited = :newVisited, p.basicInfo.views =:view where p.id = :id")
    void updateVisitedAndView(@Param("newVisited") String newVisited, @Param("view") int view, @Param("id") Long id);
}
