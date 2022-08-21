package rePashion.server.domain.preference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.preference.model.Preference;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
