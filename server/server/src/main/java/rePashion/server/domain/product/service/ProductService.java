package rePashion.server.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.product.dto.ProductCreateDto;
import rePashion.server.domain.product.exception.ProductNotExistedException;
import rePashion.server.domain.product.model.*;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.product.model.embedded.SellerNote;
import rePashion.server.domain.product.model.measure.MeasureConfig;
import rePashion.server.domain.product.model.measure.MeasureType;
import rePashion.server.domain.product.model.measure.exception.MeasureException;
import rePashion.server.domain.product.repository.ProductRepository;
import rePashion.server.global.error.exception.ErrorCode;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(ProductCreateDto dto){

        BasicInfo basicInfo = BasicInfo.builder()
                .title(dto.getBasicInfo().getTitle())
                .contact(dto.getContact())
                .category(dto.getBasicInfo().getCategory())
                .brand(dto.getBasicInfo().getBrand())
                .thumbnailImage(dto.getImgList().get(0))
                .price(dto.getPrice())
                .isIncludeDelivery(dto.getIsIncludeDelivery())
                .size(dto.getSize())
                .build();

        SellerNote sellerNote = SellerNote.builder()
                .purchasePlace(dto.getAdditionalInfo().getPurchasePlace())
                .purchaseTime(dto.getAdditionalInfo().getPurchaseTime())
                .pollution(dto.getSellerNote().getPollution())
                .height(dto.getSellerNote().getHeight())
                .condition(dto.getSellerNote().getCondition())
                .bodyShape(dto.getSellerNote().getBodyShape())
                .length(dto.getSellerNote().getLength())
                .fit(dto.getSellerNote().getFit())
                .tag(dto.getStyle().getTag())
                .color(dto.getStyle().getColor())
                .material(dto.getStyle().getMaterial())
                .opinion(dto.getOpinion())
                .build();

        MeasureType measureType;
        try {
            measureType = MeasureType.valueOf(dto.getMeasureType());
        }catch (IllegalArgumentException e){
            throw new MeasureException(ErrorCode.MEASURE_DATA_ERROR);
        }

        Measure measure = MeasureConfig.determinMeasure(measureType, dto.getMeasure());

        ProductAdvanceInfo advanceInfo = ProductAdvanceInfo.builder()
                .sellerNote(sellerNote)
                .measure(measure)
                .build();

        measure.setAdvanceInfo(advanceInfo);

        Product product = Product.builder()
                .basicInfo(basicInfo)
                .build();

        advanceInfo.changeProduct(product);

        for(String image : dto.getImgList()){
            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .imagePath(image)
                    .build();
            productImage.changeProduct(product);
        }

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    public Long update(Long productId, ProductCreateDto dto){
        delete(productId);
        Product savedProduct = save(dto);
        return savedProduct.getId();
    }

    public void delete(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(ProductNotExistedException::new);
        productRepository.delete(product);
    }

    public ProductCreateDto get(Long productId){
        Product product = productRepository.findProductEntityGraph(productId).orElseThrow(ProductNotExistedException::new);
        return ProductCreateDto.toDto(product);
    }
}
