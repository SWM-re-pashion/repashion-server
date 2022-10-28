package rePashion.server.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import java.util.ArrayList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BasicInfo{
        private String title;
        private String category;
        private String brand;

        @QueryProjection
        public BasicInfo(String title, String category, String brand) {
            this.title = title;
            this.category = category;
            this.brand = brand;
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AdditionalInfo{
        private String purchaseTime;
        private String purchasePlace;

        @QueryProjection
        public AdditionalInfo(String purchaseTime, String purchasePlace) {
            this.purchaseTime = purchaseTime;
            this.purchasePlace = purchasePlace;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SellerNote{
        private String condition;
        private String pollution;
        private int height;
        private String bodyShape;
        private String length;
        private String fit;

        @QueryProjection
        public SellerNote(String condition, String pollution, int height, String bodyShape, String length, String fit) {
            this.condition = condition;
            this.pollution = pollution;
            this.height = height;
            this.bodyShape = bodyShape;
            this.length = length;
            this.fit = fit;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Style{
        private String tag;
        private String color;
        private String material;

        @QueryProjection
        public Style(String tag, String color, String material) {
            this.tag = tag;
            this.color = color;
            this.material = material;
        }
    }
    private ArrayList<String> imgList;
    private String contact;
    private BasicInfo basicInfo;
    private int price;

    private Boolean isIncludeDelivery;
    private String size;
    private AdditionalInfo additionalInfo;
    private SellerNote sellerNote;
    private Style style;
    private MeasureDto measure;
    private String opinion;
    private String measureType;

    @QueryProjection
    public ProductDto(String contact, BasicInfo basicInfo, int price, Boolean isIncludeDelivery, String size, AdditionalInfo additionalInfo, SellerNote sellerNote, Style style, String opinion, String measureType) {
        this.contact = contact;
        this.basicInfo = basicInfo;
        this.price = price;
        this.isIncludeDelivery = isIncludeDelivery;
        this.size = size;
        this.additionalInfo = additionalInfo;
        this.sellerNote = sellerNote;
        this.style = style;
        this.opinion = opinion;
        this.measureType = measureType;
    }

    public void changeImgList(ArrayList<String> productImage){
        this.imgList = productImage;
    }

    public void changeMeasure(MeasureDto measureDto){
        this.measure = measureDto;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }
}
