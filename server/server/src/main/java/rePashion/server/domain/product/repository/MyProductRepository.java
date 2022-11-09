package rePashion.server.domain.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import rePashion.server.domain.product.dto.ProductPreviewDto;
import rePashion.server.domain.product.dto.QProductPreviewDto;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.QProduct;
import rePashion.server.domain.product.model.QProductAdvanceInfo;
import rePashion.server.domain.user.model.PurchaseStatus;
import rePashion.server.domain.user.model.QUserProduct;
import rePashion.server.domain.user.model.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyProductRepository {

    private final JPAQueryFactory queryFactory;

    QProduct product = QProduct.product;
    QUserProduct userProduct = QUserProduct.userProduct;

    public Page<ProductPreviewDto> getBySeller(User currentUser, boolean isSoldOut, Pageable pageable){
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
                .leftJoin(product.users, userProduct)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(userProduct.purchaseStatus.eq(PurchaseStatus.Seller), userProduct.user.eq(currentUser), whetherSelling(isSoldOut))
                .orderBy(product.modifiedDate.desc())
                .fetch();

        List<Product> fetchedProduct = queryFactory
                .selectFrom(product)
                .leftJoin(product.users, userProduct)
                .where(userProduct.purchaseStatus.eq(PurchaseStatus.Seller), userProduct.user.eq(currentUser), whetherSelling(isSoldOut))
                .fetch();

        return new PageImpl<>(content, pageable , fetchedProduct.size());
    }

    private BooleanExpression whetherSelling(boolean isSoldOut) {
        return product.basicInfo.status.eq(isSoldOut);
    }
}
