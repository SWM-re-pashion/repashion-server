package rePashion.server.domain.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import rePashion.server.domain.product.dto.*;
import rePashion.server.domain.product.model.QProduct;
import rePashion.server.domain.product.model.QProductAdvanceInfo;
import rePashion.server.domain.product.model.QProductImage;
import rePashion.server.domain.product.model.measure.entity.Measure;
import rePashion.server.domain.product.model.measure.entity.QMeasure;

import java.util.ArrayList;

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
            productDto.changeImgList(findProductImage(productId));
            productDto.changeMeasure(findMeasure(productId));
        }
        return productDto;
    }

    @Override
    public ProductDetailDto getDetail(Long id) {
        return null;
    }

    private ArrayList<String> findProductImage(Long productId){
        QProductImage productImage = QProductImage.productImage;
        return new ArrayList<>(queryFactory
                .select(productImage.imagePath)
                .from(productImage)
                .where(productImage.product.id.eq(productId))
                .fetch());
    }

    private MeasureDto findMeasure(Long productId){
        QMeasure measure = QMeasure.measure;
        QProductAdvanceInfo productAdvanceInfo = new QProductAdvanceInfo("productAdvanceInfo_2");
        Measure findMeasure = queryFactory
                .selectFrom(measure)
                .join(measure.advanceInfo, productAdvanceInfo)
                .where(measure.advanceInfo.product.id.eq(productId))
                .fetchOne();
        return (findMeasure == null)? null : findMeasure.getMeasureDto();
    }
}
