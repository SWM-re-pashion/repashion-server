package rePashion.server.domain.product.repository;

import com.querydsl.core.types.OrderSpecifier;
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
import rePashion.server.domain.product.model.QProduct;
import rePashion.server.domain.statics.model.filter.Order;

import java.util.List;

import static org.aspectj.util.LangUtil.isEmpty;

@Repository
@RequiredArgsConstructor
public class ProductSearchRepository {

    private final JPAQueryFactory queryFactory;

    QProduct product = QProduct.product;

    public Page<ProductPreviewDto> search(ProductSearchCond cond, Pageable pageable){

        List<ProductPreviewDto> content = queryFactory
                .select(new QProductPreviewDto(
                        product.id,
                        product.basicInfo.thumbnailImage,
                        product.basicInfo.size,
                        product.basicInfo.likes,
                        product.basicInfo.price,
                        product.basicInfo.status,
                        product.modifiedDate
                        ))
                .from(product)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(productCategoryEq(cond.getCategory()))
                .orderBy(productOrderIs(cond.getOrder()))
                .fetch();

        return new PageImpl<>(content, pageable , content.size());
    }

    private OrderSpecifier<?> productOrderIs(Order order) {
        switch (order){
            case LOW_PRICE:
                return product.basicInfo.price.asc();
            case HIGH_PRICE:
                return product.basicInfo.price.desc();
            case LIKE:
                return product.basicInfo.likes.desc();
            case VIEW:
                return product.basicInfo.views.desc();
            case LATEST:
                return product.modifiedDate.desc();
            default:
                throw new UndefinedOrderException();
        }
    }

    private BooleanExpression productCategoryEq(String category) {
        return isEmpty(category) ? null : product.basicInfo.category.eq(category);
    }
}
