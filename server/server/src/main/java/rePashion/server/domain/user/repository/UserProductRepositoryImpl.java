package rePashion.server.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.QProduct;
import rePashion.server.domain.user.model.PurchaseStatus;
import rePashion.server.domain.user.model.QUser;
import rePashion.server.domain.user.model.QUserProduct;
import rePashion.server.domain.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserProductRepositoryImpl implements UserProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    QUserProduct userProduct = QUserProduct.userProduct;
    QUser user = QUser.user;
    QProduct product = QProduct.product;

    @Override
    public int getNumberOfPurchasingByUser(Long id){
        return queryFactory
                .selectFrom(userProduct)
                .where(userProduct.user.id.eq(id))
                .fetch().size();
    }

    @Override
    public Optional<User> findProductBuyer(Product findProduct) {
        User findUser = queryFactory
                .select(user)
                .from(userProduct)
                .join(userProduct.product, product)
                .join(userProduct.user, user)
                .where(product.eq(findProduct), userProduct.purchaseStatus.eq(PurchaseStatus.Buyer))
                .fetchOne();
        return Optional.ofNullable(findUser);
    }
}
