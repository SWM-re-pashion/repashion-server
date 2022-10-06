package rePashion.server.domain.statics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.statics.model.Statics;

import java.util.List;

public interface StaticsRepository extends JpaRepository<Statics, Long> {

    public List<Statics> findStaticsByTypeAndClassificationOrderById(Statics.Type type, Statics.Classification classification);
}
