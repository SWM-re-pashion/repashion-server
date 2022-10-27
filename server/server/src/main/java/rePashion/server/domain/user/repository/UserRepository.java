package rePashion.server.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rePashion.server.domain.user.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.id = :id")
    Optional<User> findUserById(@Param("id") Long id);

    @Query("select u from User u where u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query("select u from User u where u.id = :id")
    Optional<User> findById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update User u set u.nickName = :nickName, u.profile = :profile where u.id = :id")
    void update(@Param("nickName") String nickName, @Param("profile") String profile, @Param("id") Long id);
}
