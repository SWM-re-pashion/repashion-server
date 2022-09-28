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
import rePashion.server.domain.product.resources.request.Condition;
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
    Condition.Filter cond;

    public Page<ProductPreviewDto> get(Condition.Filter cond, Pageable pageable){

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
                        productSizeEq(cond.getClothesSize())
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
                        productSizeEq(cond.getClothesSize())
                )
                .fetch().size();

        return new PageImpl<>(content, pageable , count);
    }

    private BooleanBuilder productStyleEq(String styles){
        if(styles == null) return null;
        List<String> splitStyles = List.of(styles.split(","));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        splitStyles.forEach((style) -> booleanBuilder.or(isEmpty(style) ? null : advanceInfo.sellerNote.tag.eq(style)));
        return booleanBuilder;
    }

    private BooleanBuilder productColorEq(String colors){
        if(colors == null) return null;
        List<String> splitColors = List.of(colors.split(","));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        splitColors.forEach((color) -> booleanBuilder.or(isEmpty(color) ? null : advanceInfo.sellerNote.color.eq(color)));
        return booleanBuilder;
    }

    private BooleanBuilder productFitEq(String fits){
        if(fits == null) return null;
        List<String> splitFits = List.of(fits.split(","));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        splitFits.forEach((fit) -> booleanBuilder.or(isEmpty(fit) ? null : advanceInfo.sellerNote.fit.eq(fit)));
        return booleanBuilder;
    }

    private BooleanBuilder productLengthEq(String lengths){
        if(lengths == null) return null;
        List<String> splitLengths = List.of(lengths.split(","));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        splitLengths.forEach((length) -> booleanBuilder.or(isEmpty(length) ? null : advanceInfo.sellerNote.length.eq(length)));
        return booleanBuilder;
    }

    private BooleanBuilder productSizeEq(String sizes){
        if(sizes == null) return null;
        List<String> splitSizes = List.of(sizes.split(","));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        splitSizes.forEach((size) -> booleanBuilder.or(isEmpty(size) ? null : product.basicInfo.size.eq(size)));
        return booleanBuilder;
    }

    private BooleanExpression productCategoryEq(String category) {
        return isEmpty(category) ? null : product.basicInfo.category.startsWith(convertStringWhenMeansAll(category));
    }

    private String convertStringWhenMeansAll(String category){  // 끝자리가 000으로 끝나는 전체 카테고리를 의미할 경우에 "000"을 제거해서 보내준다
        return (category.endsWith("000")) ? category.substring(0, category.length() - 3) : category;
    }

    private BooleanExpression productPriceLoe(Integer priceLoe){
        return priceLoe == null ? null : product.basicInfo.price.loe(priceLoe);
    }

    private BooleanExpression productPriceGoe(Integer priceGoe){
        return priceGoe == null ? null : product.basicInfo.price.goe(priceGoe);
    }

    private BooleanExpression productHideStatusEq(Boolean status){
        return (status == null) ? null : product.basicInfo.status.eq(!status);
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
