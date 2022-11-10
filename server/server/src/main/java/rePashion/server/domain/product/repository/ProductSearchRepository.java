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
import rePashion.server.domain.product.dto.ProductPreviewDto;
import rePashion.server.domain.product.dto.ProductSearchCond;
import rePashion.server.domain.product.dto.QProductPreviewDto;
import rePashion.server.domain.product.exception.UndefinedOrderException;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.QProduct;
import rePashion.server.domain.product.model.QProductAdvanceInfo;
import rePashion.server.domain.product.resources.request.Condition;
import rePashion.server.domain.statics.model.filter.Order;

import java.util.List;

import static org.aspectj.util.LangUtil.isEmpty;

@Repository
@RequiredArgsConstructor
public class ProductSearchRepository {

    private final JPAQueryFactory queryFactory;

    QProduct product = QProduct.product;
    QProductAdvanceInfo advanceInfo = QProductAdvanceInfo.productAdvanceInfo;

    public Page<ProductPreviewDto> search(Condition.SearchCond cond, Pageable pageable){

        List<ProductPreviewDto> content = queryFactory
                .select(new QProductPreviewDto(
                        product.id,
                        product.basicInfo.thumbnailImage,
                        product.basicInfo.title,
                        product.basicInfo.size,
                        product.basicInfo.likes,
                        product.basicInfo.price,
                        product.basicInfo.status,
                        product.modifiedDate
                        ))
                .from(product)
                .leftJoin(product.advanceInfo, advanceInfo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(productTitleOrContentContains(cond.getValue()), productHideStatusEq(cond.getHideSold()))
                .orderBy(productOrderIs(cond.getOrder()))
                .fetch();

        List<Product> fetchedProduct = queryFactory
                .selectFrom(product)
                .leftJoin(product.advanceInfo, advanceInfo)
                .where(productTitleOrContentContains(cond.getValue()), productHideStatusEq(cond.getHideSold()))
                .fetch();

        return new PageImpl<>(content, pageable , fetchedProduct.size());
    }


    private BooleanBuilder productTitleOrContentContains(String value) {
        if(value == null) return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        return booleanBuilder
                .or(product.basicInfo.title.contains(value))
                .or(advanceInfo.sellerNote.opinion.contains(value));
    }

    private OrderSpecifier<?> productOrderIs(Order order) {
        switch (order){
            case low_price:
                return product.basicInfo.price.asc();
            case high_price:
                return product.basicInfo.price.desc();
            case like:
                return product.basicInfo.likes.desc();
            case view:
                return product.basicInfo.views.desc();
            case latest:
                return product.modifiedDate.desc();
            default:
                throw new UndefinedOrderException();
        }
    }

    private BooleanExpression productHideStatusEq(Boolean status){
        return (status == null || !status) ? null : product.basicInfo.status.eq(false);
    }
}
