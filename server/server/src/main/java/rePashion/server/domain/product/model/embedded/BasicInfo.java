package rePashion.server.domain.product.model.embedded;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicInfo{
    private String title;
    private String contact;
    private String category;
    private String brand;
    private String thumbnailImage;
    private int price;
    private boolean isIncludeDelivery;
    private String size;
    private int views;

    @Builder
    public BasicInfo(String title, String contact, String category, String brand, String thumbnailImage, int price, boolean isIncludeDelivery, String size, int views) {
        this.title = title;
        this.contact = contact;
        this.category = category;
        this.brand = brand;
        this.thumbnailImage = thumbnailImage;
        this.price = price;
        this.isIncludeDelivery = isIncludeDelivery;
        this.size = size;
        this.views = views;
    }
}
