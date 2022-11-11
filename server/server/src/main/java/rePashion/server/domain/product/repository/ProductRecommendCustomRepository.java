package rePashion.server.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import rePashion.server.domain.product.exception.UndefinedOrderException;
import rePashion.server.domain.product.model.ProductRecommend;
import rePashion.server.domain.product.model.QProduct;
import rePashion.server.domain.product.model.QProductRecommend;
import rePashion.server.domain.product.resources.request.Condition;
import rePashion.server.domain.statics.model.filter.Order;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRecommendCustomRepository {

    private final JPAQueryFactory queryFactory;
    private QProductRecommend productRecommend = QProductRecommend.productRecommend;
    private QProduct product = new QProduct("product");
    private QProduct association = new QProduct("association");

    public Page<ProductRecommend> get(Condition.Recommend cond, Pageable pageable){
        List<ProductRecommend> content = queryFactory
                .selectFrom(productRecommend)
                .leftJoin(productRecommend.product, product).fetchJoin()
                .leftJoin(productRecommend.association, association).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(productHideStatusEq(cond.getHideSold()), productGenderEq(cond.getGender()))
                .orderBy(productOrderIs(cond.getOrder()))
                .fetch();
        List<ProductRecommend> fetchedProductCommend = queryFactory
                .selectFrom(productRecommend)
                .leftJoin(productRecommend.product, product).fetchJoin()
                .leftJoin(productRecommend.association, association).fetchJoin()
                .where(productHideStatusEq(cond.getHideSold()))
                .fetch();
        return new PageImpl<>(content, pageable, fetchedProductCommend.size());
    }

    private BooleanBuilder productGenderEq(Byte gender) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (gender == 1 || gender == 2){
            String prefix = String.valueOf(gender);
            booleanBuilder.and(product.basicInfo.category.startsWith(prefix));
            booleanBuilder.and(association.basicInfo.category.startsWith(prefix));
        }
        booleanBuilder.or(product.basicInfo.category.startsWith("3"));      // 공용 데이터도 추가한다
        booleanBuilder.or(association.basicInfo.category.startsWith("3"));
        return booleanBuilder;
    }
    private OrderSpecifier<?> productOrderIs(Order order) {
        switch (order){
            case low_price:
                return product.basicInfo.price.add(association.basicInfo.price).asc();
            case high_price:
                return product.basicInfo.price.add(association.basicInfo.price).desc();
            case like:
                return product.basicInfo.likes.add(association.basicInfo.likes).desc();
            case view:
                return product.basicInfo.views.add(association.basicInfo.views).desc();
            case latest:
                return product.modifiedDate.desc();
            default:
                throw new UndefinedOrderException();
        }
    }

    private BooleanExpression productHideStatusEq(Boolean status){
        return (status == null || !status) ? null : product.basicInfo.status.eq(false).and(association.basicInfo.status.eq(false));
    }
}
