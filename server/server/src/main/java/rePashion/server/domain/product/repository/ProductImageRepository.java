package rePashion.server.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.product.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
