package rePashion.server.domain.product.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCreateDto {
    private ArrayList<String> imgList;
    private String contact;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BasicInfo{
        private String title;
        private String category;
        private String brand;

        public BasicInfo(String title, String category, String brand) {
            this.title = title;
            this.category = category;
            this.brand = brand;
        }
    }
    private BasicInfo basicInfo;
    private int price;
    private Boolean isIncludeDelivery;
    private String size;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AdditionalInfo{
        private String purchaseTime;
        private String purchasePlace;

        public AdditionalInfo(String purchaseTime, String purchasePlace) {
            this.purchaseTime = purchaseTime;
            this.purchasePlace = purchasePlace;
        }
    }
    private AdditionalInfo additionalInfo;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SellerNote{
        private String condition;
        private String pollution;
        private int height;
        private String bodyShape;
        private String length;
        private String fit;

        public SellerNote(String condition, String pollution, int height, String bodyShape, String length, String fit) {
            this.condition = condition;
            this.pollution = pollution;
            this.height = height;
            this.bodyShape = bodyShape;
            this.length = length;
            this.fit = fit;
        }
    }
    private SellerNote sellerNote;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Style{
        private String tag;
        private String color;
        private String material;

        public Style(String tag, String color, String material) {
            this.tag = tag;
            this.color = color;
            this.material = material;
        }
    }
    private Style style;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Measure{
        private int length;
        private int shoulderWidth = -1;
        private int waistSection = -1;
        private int chestSection = -1;
        private int thighSection = -1;
        private int bottomSection = -1;
        private int rise = -1;
        private int sleeveLength = -1;

        public Measure(int length, int shoulderWidth, int waistSection, int chestSection, int thighSection, int bottomSection, int rise, int sleeveLength) {
            this.length = length;
            this.shoulderWidth = shoulderWidth;
            this.waistSection = waistSection;
            this.chestSection = chestSection;
            this.thighSection = thighSection;
            this.bottomSection = bottomSection;
            this.rise = rise;
            this.sleeveLength = sleeveLength;
        }
    }
    private Measure measure;
    private String opinion;

    public ProductCreateDto(ArrayList<String> imgList, String contact, BasicInfo basicInfo, int price, Boolean isIncludeDelivery, String size, AdditionalInfo additionalInfo, SellerNote sellerNote, Style style, Measure measure, String opinion) {
        this.imgList = imgList;
        this.contact = contact;
        this.basicInfo = basicInfo;
        this.price = price;
        this.isIncludeDelivery = isIncludeDelivery;
        this.size = size;
        this.additionalInfo = additionalInfo;
        this.sellerNote = sellerNote;
        this.style = style;
        this.measure = measure;
        this.opinion = opinion;
    }
}
