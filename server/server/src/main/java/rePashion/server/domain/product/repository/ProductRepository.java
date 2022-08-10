package rePashion.server.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
