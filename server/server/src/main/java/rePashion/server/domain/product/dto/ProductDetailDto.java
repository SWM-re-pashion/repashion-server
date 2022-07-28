package rePashion.server.domain.product.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailDto {

    private Boolean isMe;
    public static final String STANDARD_PROFILE_IMAGE = "https://webserver0712.s3.ap-northeast-2.amazonaws.com/profile/%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84.png";

    @Getter
    public static class SellerInfo{
        private String profileImg;
        private String nickname;
        private ArrayList<String> image;

        public SellerInfo(String profileImg, String nickname, ArrayList<String> image) {
            this.profileImg = profileImg;
            this.nickname = nickname;
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

        public Basic(String title, String classification, String brand, String productInfo, String styleInfo) {
            this.title = title;
            this.classification = classification;
            this.brand = brand;
            this.productInfo = productInfo;
            this.styleInfo = styleInfo;
        }
    }
    private Basic basic;

    @Getter
    public static class SellerNotice{
        private String condition;
        private String pollution;
        private String height;
        private String length;
        private String bodyForm;
        private String fit;
        private String purchaseTime;
        private String purchasePlace;

        public SellerNotice(String condition, String pollution, String height, String length, String bodyForm, String fit, String purchaseTime, String purchasePlace) {
            this.condition = condition;
            this.pollution = pollution;
            this.height = height;
            this.length = length;
            this.bodyForm = bodyForm;
            this.fit = fit;
            this.purchaseTime = purchaseTime;
            this.purchasePlace = purchasePlace;
        }
    }
    private SellerNotice sellerNotice;

    @Getter
    public static class Measure{
        private int length;
        private int shoulderWidth;
        private int chestSection;
        private int sleeveLength;

        public Measure(int length, int shoulderWidth, int chestSection, int sleeveLength) {
            this.length = length;
            this.shoulderWidth = shoulderWidth;
            this.chestSection = chestSection;
            this.sleeveLength = sleeveLength;
        }
    }
    private Measure measure;
    private String opinion;
    private int price;
    private Boolean isIncludeDelivery;
    private String updatedAt;
    private int like;
    private int view;

    @Builder
    public ProductDetailDto(Boolean isMe, SellerInfo sellerInfo, Basic basic, SellerNotice sellerNotice, Measure measure, String opinion, int price, boolean isIncludeDelivery, LocalDateTime updatedAt, int like, int view) {
        this.isMe = isMe;
        this.sellerInfo = sellerInfo;
        this.basic = basic;
        this.sellerNotice = sellerNotice;
        this.measure = measure;
        this.opinion = opinion;
        this.price = price;
        this.isIncludeDelivery = isIncludeDelivery;
        this.updatedAt = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.like = like;
        this.view = view;
    }
}
