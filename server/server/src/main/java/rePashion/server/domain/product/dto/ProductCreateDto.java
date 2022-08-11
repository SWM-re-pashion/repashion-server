package rePashion.server.domain.product.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.Product;

import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<String, Integer> measure;
    private String opinion;

    @Builder
    public ProductCreateDto(ArrayList<String> imgList, String contact, BasicInfo basicInfo, int price, Boolean isIncludeDelivery, String size, AdditionalInfo additionalInfo, SellerNote sellerNote, Style style, HashMap<String, Integer> measure, String opinion) {
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

    static public ProductCreateDto toDto(Product product){
        BasicInfo basicInfo = new BasicInfo(product.getBasicInfo().getTitle(), product.getBasicInfo().getCategory(), product.getBasicInfo().getBrand());
        AdditionalInfo additionalInfo = new AdditionalInfo(product.getAdvanceInfo().getSellerNote().getPurchaseTime(), product.getAdvanceInfo().getSellerNote().getPurchasePlace());
        SellerNote sellerNote = new SellerNote(product.getAdvanceInfo().getSellerNote().getConditions(), product.getAdvanceInfo().getSellerNote().getPollution(), product.getAdvanceInfo().getSellerNote().getHeight(), product.getAdvanceInfo().getSellerNote().getBodyShape(), product.getAdvanceInfo().getSellerNote().getLength(), product.getAdvanceInfo().getSellerNote().getFit());
        Style style = new Style(product.getAdvanceInfo().getSellerNote().getTag(), product.getAdvanceInfo().getSellerNote().getColor(), product.getAdvanceInfo().getSellerNote().getMaterial());
        HashMap<String, Integer> measure = product.getAdvanceInfo().getMeasure().getMap();
        return ProductCreateDto.builder()
                .imgList(product.toStringArray())
                .contact(product.getBasicInfo().getContact())
                .basicInfo(basicInfo)
                .price(product.getBasicInfo().getPrice())
                .isIncludeDelivery(product.getBasicInfo().isIncludeDelivery())
                .size(product.getBasicInfo().getSize())
                .additionalInfo(additionalInfo)
                .sellerNote(sellerNote)
                .style(style)
                .measure(measure)
                .opinion(product.getAdvanceInfo().getSellerNote().getOpinion())
                .build();
    }
}
