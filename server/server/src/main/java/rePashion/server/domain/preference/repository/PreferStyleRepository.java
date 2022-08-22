package rePashion.server.domain.preference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.preference.model.PreferStyle;

public interface PreferStyleRepository extends JpaRepository<PreferStyle, Long> {
}
