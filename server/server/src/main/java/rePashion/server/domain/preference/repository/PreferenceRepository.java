package rePashion.server.domain.preference.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import rePashion.server.domain.preference.model.Preference;
import rePashion.server.domain.user.model.User;

import java.util.Optional;
import java.util.function.Function;

public interface PreferenceRepository extends JpaRepository<Preference, Long>, PreferenceCustomRepository{

    int countPreferenceByUser(User user);

}
