package rePashion.server.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.product.model.measure.entity.Measure;


public interface MeasureRepository extends JpaRepository<Measure, Long> {
}
