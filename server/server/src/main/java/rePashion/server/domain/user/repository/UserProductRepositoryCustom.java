package rePashion.server.domain.user.repository;

import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.user.model.User;

import java.util.Optional;

public interface UserProductRepositoryCustom {

    int getNumberOfPurchasingByUser(Long id);

    Optional<User> findProductBuyer(Product product);

    Optional<User> findByProductSeller(Product product);
}
