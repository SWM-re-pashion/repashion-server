package rePashion.server.domain.product.dto;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRequestBody {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class BasicInfo{
        private String title;
        private String category;
        private String brand;
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class AdditionalInfo{
        private String purchaseTime;
        private String purchasePlace;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SellerNote{
        private String condition;
        private String pollution;
        private int height;
        private String bodyShape;
        private String length;
        private String fit;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Style{
        private String tag;
        private String color;
        private String material;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Measure{
        private Integer length;
        private Integer shoulderWidth;
        private Integer chestSection;
        private Integer sleeveLength;
        private Integer waistSection;
        private Integer bottomSection;
        private Integer thighSection;
        private Integer rise;
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
    private Measure measure;
    private String opinion;
    private String measureType;

    public ProductRequestBody(ArrayList<String> imgList, String contact, BasicInfo basicInfo, int price, Boolean isIncludeDelivery, String size, AdditionalInfo additionalInfo, SellerNote sellerNote, Style style, Measure measure, String opinion, String measureType) {
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
        this.measureType = measureType;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public String getContact() {
        return contact;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public int getPrice() {
        return price;
    }

    public Boolean getIncludeDelivery() {
        return isIncludeDelivery;
    }

    public String getSize() {
        return size;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public SellerNote getSellerNote() {
        return sellerNote;
    }

    public Style getStyle() {
        return style;
    }

    public Measure getMeasure() {
        return measure;
    }

    public String getOpinion() {
        return opinion;
    }

    public String getMeasureType() {
        return measureType;
    }
}
