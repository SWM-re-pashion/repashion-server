package rePashion.server.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.user.model.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
}
