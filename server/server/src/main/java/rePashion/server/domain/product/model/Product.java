package rePashion.server.domain.product.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.user.model.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String opinion;
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
    private int view;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products")
    private User seller;

    @Builder
    public Product(Long id, String title, String category, String brand, int price, boolean isIncludeDelivery, String gender, String size, String purchaseTime, String purchasePlace, String condition, String pollution, int height, String bodyShape, String length, String fit, String opinion, String tag, String color, String material, int totalLength, int shoulderWidth, int waistSection, int chestSection, int thighSection, int bottomSection, int rise, int sleeveLength, int view, LocalDateTime updatedAt, LocalDateTime createdAt, User seller) {
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
        this.opinion = opinion;
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
        this.view = view;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.seller = seller;
    }

    public void updateTime(){
        this.updatedAt = LocalDateTime.now();
    }

    public void updateView() { this.view++;
    }

    // mock 객체 하나 만들어서 보내주기
    public static Product MockProduct(){
        return Product.builder()
                .title("스투시 프린팅 티셔츠")
                .category("상의")
                .brand("스투시")
                .price(2000)
                .isIncludeDelivery(true)
                .gender("남성")
                .size("XL")
                .purchaseTime("한달_전")
                .purchasePlace("스투시 온라인 매장")
                .condition("새 상품")
                .pollution("전혀_없음")
                .height(170)
                .bodyShape("뚱뚱")
                .length("크롭")
                .fit("타이트")
                .opinion("안 입는데 옷장에 있어서")
                .tag("힙합")
                .color("블랙")
                .material("퍼")
                .totalLength(73)
                .shoulderWidth(53)
                .waistSection(-1)
                .chestSection(59)
                .thighSection(-1)
                .bottomSection(-1)
                .rise(-1)
                .sleeveLength(100)
                .build();
    }
}
