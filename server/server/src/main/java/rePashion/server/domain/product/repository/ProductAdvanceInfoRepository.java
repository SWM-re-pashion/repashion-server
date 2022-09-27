package rePashion.server.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.product.model.ProductAdvanceInfo;

public interface ProductAdvanceInfoRepository extends JpaRepository<ProductAdvanceInfo, Long> {
}
