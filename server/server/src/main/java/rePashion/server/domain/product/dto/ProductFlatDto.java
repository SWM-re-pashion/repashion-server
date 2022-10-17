package rePashion.server.domain.product.dto;


import lombok.Data;

import java.util.ArrayList;

@Data
public class ProductFlatDto {
    private ArrayList<String> imgList;
    private String contact;
    private String title;
    private String category;
    private String brand;
    private int price;
    private Boolean isIncludeDelivery;
    private String size;
    private String purchaseTime;
    private String purchasePlace;
    private String condition;
    private String pollution;
    private int height;
    private String bodyShape;
    private String length;
    private String fit;
    private String tag;
    private String color;
    private String material;
    private ProductDto.Style style;
    private ProductDto.Measure measure;
    private String opinion;
    private String measureType;
}
