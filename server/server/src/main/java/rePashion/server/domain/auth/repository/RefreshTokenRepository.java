package rePashion.server.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.auth.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByKey(Long key);
}
