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

    @Transactional
    @Modifying
    @Query("update User u set u.nickName = :nickName where u.id = :id")
    void updateUserNickName(@Param("nickName") String nickName, @Param("id") Long id);
}
