package rePashion.server.domain.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import rePashion.server.domain.product.dto.*;
import rePashion.server.domain.product.model.QProduct;
import rePashion.server.domain.product.model.QProductAdvanceInfo;
import rePashion.server.domain.product.model.QProductImage;
import rePashion.server.domain.product.model.measure.entity.QMeasure;
import rePashion.server.domain.statics.category.model.QCategory;
import rePashion.server.domain.user.model.PurchaseStatus;
import rePashion.server.domain.user.model.QUser;
import rePashion.server.domain.user.model.QUserProduct;
import rePashion.server.domain.user.model.User;

import java.util.ArrayList;
import java.util.Objects;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public ProductDto get(Long productId) {
        QProduct product = QProduct.product;
        QProductAdvanceInfo productAdvanceInfo = QProductAdvanceInfo.productAdvanceInfo;

        ProductDto productDto = queryFactory
                .select(
                    new QProductDto(
                    product.basicInfo.contact,
                    new QProductDto_BasicInfo(
                            product.basicInfo.title,
                            product.basicInfo.category,
                            product.basicInfo.brand
                    ),
                    product.basicInfo.price,
                    product.basicInfo.isIncludeDelivery,
                    product.basicInfo.size,
                    new QProductDto_AdditionalInfo(
                            productAdvanceInfo.sellerNote.purchaseTime,
                            productAdvanceInfo.sellerNote.purchasePlace
                    ),
                    new QProductDto_SellerNote(
                            productAdvanceInfo.sellerNote.conditions,
                            productAdvanceInfo.sellerNote.pollution,
                            productAdvanceInfo.sellerNote.height,
                            productAdvanceInfo.sellerNote.bodyShape,
                            productAdvanceInfo.sellerNote.length,
                            productAdvanceInfo.sellerNote.fit
                    ),
                    new QProductDto_Style(
                            productAdvanceInfo.sellerNote.tag,
                            productAdvanceInfo.sellerNote.color,
                            productAdvanceInfo.sellerNote.material
                    ),
                    productAdvanceInfo.sellerNote.opinion,
                    productAdvanceInfo.measureType
                    ))
            .from(product)
            .join(product.advanceInfo, productAdvanceInfo)
            .where(product.id.eq(productId))
            .fetchOne();
        if(productDto != null) {
            productDto.changeImgList(getImageList(productId));
            productDto.changeMeasure(getMeasure(productId));
        }
        return productDto;
    }

    @Override
    public ProductDetailDto getDetail(User currentUser, Long productId) {
        QProduct product = QProduct.product;
        QUser user = QUser.user;
        QUserProduct userProduct = QUserProduct.userProduct;
        QProductAdvanceInfo advanceInfo = QProductAdvanceInfo.productAdvanceInfo;

        ProductDetailDto productDetailDto = queryFactory
                .select(new QProductDetailDto(
                        user.id.eq(currentUser.getId()),
                        product.basicInfo.status,
                        new QProductDetailDto_SellerInfo(
                                user.id,
                                user.profile,
                                user.nickName
                        ),
                        new QProductDetailDto_Basic(
                                product.basicInfo.title,
                                product.basicInfo.brand,
                                product.basicInfo.size,
                                advanceInfo.sellerNote.material,
                                advanceInfo.sellerNote.color,
                                advanceInfo.sellerNote.tag
                        ),
                        new QProductDetailDto_SellerNotice(
                                advanceInfo.sellerNote.conditions,
                                advanceInfo.sellerNote.pollution,
                                advanceInfo.sellerNote.height,
                                advanceInfo.sellerNote.length,
                                advanceInfo.sellerNote.bodyShape,
                                advanceInfo.sellerNote.fit,
                                advanceInfo.sellerNote.purchaseTime,
                                advanceInfo.sellerNote.purchasePlace
                        ),
                        advanceInfo.sellerNote.opinion,
                        product.basicInfo.price,
                        product.basicInfo.isIncludeDelivery,
                        product.modifiedDate,
                        product.basicInfo.likes,
                        product.basicInfo.views,
                        product.basicInfo.contact,
                        product.basicInfo.category
                ))
                .from(product)
                .join(product.advanceInfo, advanceInfo)
                .join(product.users, userProduct)
                .join(userProduct.user, user)
                .where(product.id.eq(productId), userProduct.purchaseStatus.eq(PurchaseStatus.Seller))
                .fetchOne();

        if(productDetailDto != null){
            productDetailDto.getSellerInfo().changeImage(getImageList(productId));
            CategoryDto categories = getCategories(productDetailDto.getCategory());
            productDetailDto.getBasic().changeClassificationAndProductInfo(categories.getGenderCategory(), categories.getParentCategory(), categories.getSubCategory());
            productDetailDto.changeMeasure(getMeasure(productId));
        }

        return productDetailDto;
    }

    private MeasureDto getMeasure(Long productId) {
        QMeasure measure = QMeasure.measure;
        return Objects.requireNonNull(queryFactory.selectFrom(measure).where(measure.product.id.eq(productId)).fetchOne()).getMeasureDto();
    }

    private CategoryDto getCategories(String category) {
        QCategory genderCategory = new QCategory("genderCategory");
        QCategory parentCategory = new QCategory("parentCategory");
        QCategory subCategory = new QCategory("subCategory");

        return queryFactory
                .select(new QCategoryDto(genderCategory.name, parentCategory.name, subCategory.name))
                .from(subCategory)
                .join(subCategory.parentCategory, parentCategory)
                .join(parentCategory.parentCategory, genderCategory)
                .where(subCategory.categoryId.eq(Long.parseLong(category)))
                .fetchOne();
    }

    private ArrayList<String> getImageList(Long productId) {
        QProductImage productImage = QProductImage.productImage;
        return (ArrayList<String>) queryFactory.select(productImage.imagePath).from(productImage).where(productImage.product.id.eq(productId)).fetch();
    }
}
