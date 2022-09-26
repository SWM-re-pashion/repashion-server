package rePashion.server.domain.product.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import rePashion.server.domain.product.dto.ProductFilterCond;
import rePashion.server.domain.product.dto.ProductPreviewDto;
import rePashion.server.domain.product.dto.QProductPreviewDto;
import rePashion.server.domain.product.exception.UndefinedOrderException;
import rePashion.server.domain.product.model.QProduct;
import rePashion.server.domain.product.model.QProductAdvanceInfo;
import rePashion.server.domain.statics.model.filter.Order;

import java.util.List;

import static org.aspectj.util.LangUtil.isEmpty;

@Repository
@RequiredArgsConstructor
public class ProductFilterRepository {
    private final JPAQueryFactory queryFactory;

    QProduct product = QProduct.product;
    QProductAdvanceInfo advanceInfo = QProductAdvanceInfo.productAdvanceInfo;

    public Page<ProductPreviewDto> get(ProductFilterCond cond, Pageable pageable){

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
                .join(product.advanceInfo, advanceInfo).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(productCategoryEq(cond.getCategory()),
                        (BooleanExpression) cond.getSize().stream().map(this::productStyleEq),
                        (BooleanExpression) cond.getColor().stream().map(this::productColorEq),
                        (BooleanExpression) cond.getFit().stream().map(this::productFitEq),
                        (BooleanExpression) cond.getLength().stream().map(this::productLengthEq),
                        (BooleanExpression) cond.getSize().stream().map(this::productSizeEq),
                        productLoe(cond.getPriceLoe()),
                        productGoe(cond.getPriceGoe())
                        )
                .orderBy(productOrderIs(cond.getOrder()))
                .fetch();

        long count = queryFactory
                .selectFrom(product)
                .join(product.advanceInfo, advanceInfo).fetchJoin()
                .where(productCategoryEq(cond.getCategory()),
                        (BooleanExpression) cond.getSize().stream().map(this::productStyleEq),
                        (BooleanExpression) cond.getColor().stream().map(this::productColorEq),
                        (BooleanExpression) cond.getFit().stream().map(this::productFitEq),
                        (BooleanExpression) cond.getLength().stream().map(this::productLengthEq),
                        (BooleanExpression) cond.getSize().stream().map(this::productSizeEq),
                        productLoe(cond.getPriceLoe()),
                        productGoe(cond.getPriceGoe()),
                        productHideStatusEq(cond.getHideSold())
                )
                .fetch().size();

        return new PageImpl<>(content, pageable , count);
    }

    private BooleanExpression productCategoryEq(String category) {
        return isEmpty(category) ? null : product.basicInfo.category.eq(category);
    }

    private BooleanExpression productStyleEq(String style){
        return isEmpty(style) ? null : advanceInfo.sellerNote.tag.eq(style);
    }

    private BooleanExpression productColorEq(String color){
        return isEmpty(color) ? null : advanceInfo.sellerNote.color.eq(color);
    }

    private BooleanExpression productFitEq(String fit){
        return isEmpty(fit) ? null : advanceInfo.sellerNote.fit.eq(fit);
    }

    private BooleanExpression productLengthEq(String length){
        return isEmpty(length) ? null : advanceInfo.sellerNote.length.eq(length);
    }

    private BooleanExpression productSizeEq(String size){
        return isEmpty(size) ? null : product.basicInfo.size.eq(size);
    }

    private BooleanExpression productLoe(Integer priceLoe){
        return priceLoe == null ? null : product.basicInfo.price.loe(priceLoe);
    }

    private BooleanExpression productGoe(Integer priceGoe){
        return priceGoe == null ? null : product.basicInfo.price.goe(priceGoe);
    }

    private BooleanExpression productHideStatusEq(Boolean status){
        return (status == null) ? null : product.basicInfo.status.eq(status);
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
}
