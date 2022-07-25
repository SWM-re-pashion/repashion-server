package rePashion.server.domain.product.model;

import lombok.Builder;

public class Product {

    private Long id;
    private String title;
    private String category;
    private String brand;
    private int price;
    private boolean isIncludeDelivery;
    private String gender;
    private String size;
    private String purchaseTime;
    private String purchasePlace;
    private String condition;
    private String pollution;
    private int height;
    private String bodyShape;
    private String length;
    private String fit;
    private String reason;
    private String tag;
    private String color;
    private String material;
    private int totalLength;
    private int shoulderWidth;
    private int waistSection;
    private int chestSection;
    private int thighSection;
    private int bottomSection;
    private int rise;
    private int sleeveLength;

    @Builder
    public Product(Long id, String title, String category, String brand, int price, boolean isIncludeDelivery, String gender, String size, String purchaseTime, String purchasePlace, String condition, String pollution, int height, String bodyShape, String length, String fit, String reason, String tag, String color, String material, int totalLength, int shoulderWidth, int waistSection, int chestSection, int thighSection, int bottomSection, int rise, int sleeveLength) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.isIncludeDelivery = isIncludeDelivery;
        this.gender = gender;
        this.size = size;
        this.purchaseTime = purchaseTime;
        this.purchasePlace = purchasePlace;
        this.condition = condition;
        this.pollution = pollution;
        this.height = height;
        this.bodyShape = bodyShape;
        this.length = length;
        this.fit = fit;
        this.reason = reason;
        this.tag = tag;
        this.color = color;
        this.material = material;
        this.totalLength = totalLength;
        this.shoulderWidth = shoulderWidth;
        this.waistSection = waistSection;
        this.chestSection = chestSection;
        this.thighSection = thighSection;
        this.bottomSection = bottomSection;
        this.rise = rise;
        this.sleeveLength = sleeveLength;
    }
}
