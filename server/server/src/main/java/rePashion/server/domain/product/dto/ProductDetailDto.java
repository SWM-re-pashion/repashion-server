package rePashion.server.domain.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import rePashion.server.domain.statics.service.StaticsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailDto {

    private Boolean isMe;

    private Boolean status;
    @Getter
    public static class SellerInfo{
        private String profileImg;
        private String nickname;
        private ArrayList<String> image;

        @QueryProjection
        public SellerInfo(String profileImg, String nickname) {
            this.profileImg = profileImg;
            this.nickname = nickname;
        }

        public void changeImage(ArrayList<String> image){
            this.image = image;
        }
    }
    private SellerInfo sellerInfo;

    @Getter
    public static class Basic{
        private String title;
        private String classification;
        private String brand;
        private String productInfo;
        private String styleInfo;

        @QueryProjection
        public Basic(String title, String brand, String size, String material, String color, String style) {
            this.title = title;
            this.brand = brand;
            this.productInfo = size;
            this.styleInfo = material + "/" + color + "/" + StaticsService.lookups.get(style);
        }

        public void changeClassificationAndProductInfo(String genderCategory, String parentCategory, String subCategory){
            this.classification = parentCategory + "/" + subCategory;
            this.productInfo = genderCategory + "/" + this.productInfo;
        }
    }
    private Basic basic;

    @Getter
    public static class SellerNotice{
        private String condition;
        private String pollution;
        private Integer height;
        private String length;
        private String bodyShape;
        private String fit;
        private String purchaseTime;
        private String purchasePlace;

        @QueryProjection
        public SellerNotice(String condition, String pollution, Integer height, String length, String bodyShape, String fit, String purchaseTime, String purchasePlace) {
            this.condition = StaticsService.lookups.get(condition);
            this.pollution = StaticsService.lookups.get(pollution);
            this.height = height;
            this.length = StaticsService.lookups.get(length);
            this.bodyShape = StaticsService.lookups.get(bodyShape);
            this.fit = StaticsService.lookups.get(fit);
            this.purchaseTime = purchaseTime;
            this.purchasePlace = purchasePlace;
        }
    }
    private SellerNotice sellerNotice;
    private MeasureDto measure;
    private String opinion;
    private int price;
    private Boolean isIncludeDelivery;
    private String updatedAt;
    private int like;
    private int view;

    @QueryProjection
    public ProductDetailDto(Boolean isMe, Boolean status, SellerInfo sellerInfo, Basic basic, SellerNotice sellerNotice, String opinion, int price, boolean isIncludeDelivery, LocalDateTime updatedAt, int like, int view) {
        this.isMe = isMe;
        this.status = status;
        this.sellerInfo = sellerInfo;
        this.basic = basic;
        this.sellerNotice = sellerNotice;
        this.opinion = opinion;
        this.price = price;
        this.isIncludeDelivery = isIncludeDelivery;
        this.updatedAt = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.like = like;
        this.view = view;
    }

    public void changeMeasure(MeasureDto measureDto){
        this.measure = measureDto;
    }
}
