package rePashion.server.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
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
    ProductFilterCond cond;

    public Page<ProductPreviewDto> get(ProductFilterCond cond, Pageable pageable){

        this.cond = cond;
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
                .where(
                        productCategoryEq(cond.getCategory()),
                        productHideStatusEq(cond.getHideSold()),
                        productPriceGoe(cond.getPriceGoe()),
                        productPriceLoe(cond.getPriceLoe()),
                        productStyleEq(cond.getStyle()),
                        productColorEq(cond.getColor()),
                        productFitEq(cond.getFit()),
                        productLengthEq(cond.getLength()),
                        productSizeEq(cond.getSize())
                    )
                .orderBy(productOrderIs(cond.getOrder()))
                .fetch();

        long count = queryFactory
                .selectFrom(product)
                .join(product.advanceInfo, advanceInfo)
                .where(
                        productCategoryEq(cond.getCategory()),
                        productHideStatusEq(cond.getHideSold()),
                        productPriceGoe(cond.getPriceGoe()),
                        productPriceLoe(cond.getPriceLoe()),
                        productStyleEq(cond.getStyle()),
                        productColorEq(cond.getColor()),
                        productFitEq(cond.getFit()),
                        productLengthEq(cond.getLength()),
                        productSizeEq(cond.getSize())
                )
                .fetch().size();

        return new PageImpl<>(content, pageable , count);
    }

    private BooleanBuilder productStyleEq(List<String> styles){
        if(styles == null) return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        styles.forEach((style) -> booleanBuilder.or(isEmpty(style) ? null : advanceInfo.sellerNote.tag.eq(style)));
        return booleanBuilder;
    }

    private BooleanBuilder productColorEq(List<String> colors){
        if(colors == null) return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        colors.forEach((color) -> booleanBuilder.or(isEmpty(color) ? null : advanceInfo.sellerNote.color.eq(color)));
        return booleanBuilder;
    }

    private BooleanBuilder productFitEq(List<String> fits){
        if(fits == null) return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        fits.forEach((fit) -> booleanBuilder.or(isEmpty(fit) ? null : advanceInfo.sellerNote.fit.eq(fit)));
        return booleanBuilder;
    }

    private BooleanBuilder productLengthEq(List<String> lengths){
        if(lengths == null) return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        lengths.forEach((length) -> booleanBuilder.or(isEmpty(length) ? null : advanceInfo.sellerNote.length.eq(length)));
        return booleanBuilder;
    }

    private BooleanBuilder productSizeEq(List<String> sizes){
        if(sizes == null) return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        sizes.forEach((size) -> booleanBuilder.or(isEmpty(size) ? null : product.basicInfo.size.eq(size)));
        return booleanBuilder;
    }

    private BooleanExpression productCategoryEq(String category) {
        return isEmpty(category) ? null : product.basicInfo.category.eq(category);
    }

    private BooleanExpression productPriceLoe(Integer priceLoe){
        return priceLoe == null ? null : product.basicInfo.price.loe(priceLoe);
    }

    private BooleanExpression productPriceGoe(Integer priceGoe){
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
