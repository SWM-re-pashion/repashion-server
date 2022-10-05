package rePashion.server.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import rePashion.server.domain.user.model.QUserProduct;

@Repository
@RequiredArgsConstructor
public class UserProductRepositoryImpl implements UserProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    QUserProduct userProduct = QUserProduct.userProduct;

    @Override
    public int getNumberOfPurchasingByUser(Long id){
        return queryFactory
                .selectFrom(userProduct)
                .where(userProduct.user.id.eq(id))
                .fetch().size();
    }
}
