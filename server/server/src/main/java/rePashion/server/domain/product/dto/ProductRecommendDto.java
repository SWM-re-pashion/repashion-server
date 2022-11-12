package rePashion.server.domain.product.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.ProductRecommend;
import rePashion.server.domain.product.model.embedded.BasicInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRecommendDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class ProductRecommendPreview{
        private Long id;
        private String img;
        private String title;
        private String size;
        private Integer like;
        private Integer price;
        private String type;
        private Boolean isSoldOut;
        private String updatedAt;

        public ProductRecommendPreview(Long id, BasicInfo basicInfo, LocalDateTime updatedAt) {
            this.id = id;
            this.img = basicInfo.getThumbnailImage();
            this.title = basicInfo.getTitle();
            this.size = basicInfo.getSize();
            this.like = basicInfo.getLikes();
            this.price = basicInfo.getPrice();
            this.type = basicInfo.getType();
            this.isSoldOut = basicInfo.getStatus();
            this.updatedAt = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    private Long id;
    private ProductRecommendPreview product;
    private ProductRecommendPreview associatedProduct;

    public ProductRecommendDto(ProductRecommend productRecommend) {
        this.id = productRecommend.getId();
        this.product = new ProductRecommendPreview(productRecommend.getProduct().getId(), productRecommend.getProduct().getBasicInfo(), productRecommend.getProduct().getModifiedDate());
        this.associatedProduct = new ProductRecommendPreview(productRecommend.getAssociation().getId(), productRecommend.getAssociation().getBasicInfo(), productRecommend.getProduct().getModifiedDate());
    }
}
