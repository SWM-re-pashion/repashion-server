package rePashion.server.domain.preference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.preference.model.Preference;
import rePashion.server.domain.user.model.User;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    int countPreferenceByUser(User user);
}
