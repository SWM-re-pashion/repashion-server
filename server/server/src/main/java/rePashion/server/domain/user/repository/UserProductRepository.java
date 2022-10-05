package rePashion.server.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rePashion.server.domain.user.model.UserProduct;

public interface UserProductRepository extends JpaRepository<UserProduct, Long>, UserProductRepositoryCustom{
}
