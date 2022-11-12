package rePashion.server.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.product.model.ProductRecommend;

public interface ProductRecommendRepository extends JpaRepository<ProductRecommend, Long> {
}
