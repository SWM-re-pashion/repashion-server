package rePashion.server.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.user.model.Preference;

import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
