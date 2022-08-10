package rePashion.server.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.product.dto.ProductCreateDto;
import rePashion.server.domain.product.exception.ProductNotExistedException;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.ProductAdvanceInfo;
import rePashion.server.domain.product.model.ProductImage;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.product.model.embedded.Measure;
import rePashion.server.domain.product.model.embedded.SellerNote;
import rePashion.server.domain.product.repository.ProductAdvanceInfoRepository;
import rePashion.server.domain.product.repository.ProductImageRepository;
import rePashion.server.domain.product.repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductAdvanceInfoRepository productAdvanceInfoRepository;

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
                .views(0)
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


        Measure measure = Measure.builder()
                .totalLength(dto.getMeasure().getLength())
                .shoulderWidth(dto.getMeasure().getShoulderWidth())
                .waistSection(dto.getMeasure().getWaistSection())
                .chestSection(dto.getMeasure().getChestSection())
                .thighSection(dto.getMeasure().getThighSection())
                .bottomSection(dto.getMeasure().getBottomSection())
                .rise(dto.getMeasure().getRise())
                .sleeveLength(dto.getMeasure().getSleeveLength())
                .build();

        ProductAdvanceInfo advanceInfo = ProductAdvanceInfo.builder()
                .sellerNote(sellerNote)
                .measure(measure)
                .build();

        Product product = Product.builder()
                .basicInfo(basicInfo)
                .build();

        Product save = productRepository.save(product);
        advanceInfo.changeProduct(save);
        productAdvanceInfoRepository.save(advanceInfo);

        for(String image : dto.getImgList()){
            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .imagePath(image)
                    .build();
            productImage.changeProduct(product);
            productImageRepository.save(productImage);
        }

        return product;
    }

    public Long update(Long productId, ProductCreateDto dto){
        return 1L;
    }

    public void delete(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(ProductNotExistedException::new);
        productRepository.delete(product);
    }

    public ProductCreateDto get(Long productId){
        return null;
    }
}
