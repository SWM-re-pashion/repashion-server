package rePashion.server.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import rePashion.server.domain.product.exception.ProductNotExistedException;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.product.model.embedded.SellerNote;
import rePashion.server.domain.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductRepository productRepository;
    private Product product;

    @Value("${secret.profile.base_url}")
    private String BASE_URL;

    public ProductDetailDto get(Long id){
        product = getProduct(id);
        return ProductDetailDto.builder()
                .isMe(getIsMe())
                .status(getStatus())
                .sellerInfo(getSellerInfo())
                .basic(getBasic())
                .sellerNotice(getSellerNotice())
                .measure(product.getAdvanceInfo().getMeasure().getMap())
                .opinion(product.getAdvanceInfo().getSellerNote().getOpinion())
                .price(product.getBasicInfo().getPrice())
                .isIncludeDelivery(product.getBasicInfo().isIncludeDelivery())
                .updatedAt(product.getModifiedDate())
                .like(getLike())
                .view(getView())
                .build();
    }


    private Boolean getIsMe() {
        return null;
    }

    private String getStatus(){
        return null;
    }

    private ProductDetailDto.SellerInfo getSellerInfo() {
        return new ProductDetailDto.SellerInfo(getUserProfileImage(),getCurrentUser(), product.toStringArray());
    }

    private String getCurrentUser(){
        return null;
    }
    private String getUserProfileImage(){
        return BASE_URL;
    }

    private ProductDetailDto.Basic getBasic() {
        BasicInfo basicInfo = product.getBasicInfo();
        String[] splitCategory = basicInfo.getCategory().split("/");
        return new ProductDetailDto.Basic(
                basicInfo.getTitle(),
                splitCategory[1] + "/" + splitCategory[2],
                basicInfo.getBrand(),
                splitCategory[0] + "/" + basicInfo.getSize(),
                product.getAdvanceInfo().getSellerNote().getMaterial()
                        + "/" + product.getAdvanceInfo().getSellerNote().getColor()
                        + "/" + product.getAdvanceInfo().getSellerNote().getTag()
        );
    }

    private ProductDetailDto.SellerNotice getSellerNotice() {
        SellerNote sellerNote = product.getAdvanceInfo().getSellerNote();
        return new ProductDetailDto.SellerNotice(
                sellerNote.getConditions(),
                sellerNote.getPollution(),
                String.valueOf(sellerNote.getHeight()),
                sellerNote.getLength(),
                sellerNote.getFit(),
                sellerNote.getBodyShape(),
                sellerNote.getPurchaseTime(),
                sellerNote.getPurchasePlace()
        );
    }

    private int getLike() {
        return 0;
    }

    private int getView(){
        return 0;
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotExistedException::new);
    }
}
