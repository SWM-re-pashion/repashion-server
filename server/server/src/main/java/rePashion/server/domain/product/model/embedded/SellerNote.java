package rePashion.server.domain.product.model.embedded;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellerNote {
    private String purchaseTime;
    private String purchasePlace;
    private String pollution;
    private int height;
    private String conditions;
    private String bodyShape;
    private String length;
    private String fit;
    private String tag;
    private String color;
    private String material;
    private String opinion;

    @Builder
    public SellerNote(String purchaseTime, String purchasePlace, String condition, String pollution, int height, String bodyShape, String length, String fit, String tag, String color, String material, String opinion) {
        this.purchaseTime = purchaseTime;
        this.purchasePlace = purchasePlace;
        this.pollution = pollution;
        this.height = height;
        this.conditions = condition;
        this.bodyShape = bodyShape;
        this.length = length;
        this.fit = fit;
        this.tag = tag;
        this.color = color;
        this.material = material;
        this.opinion = opinion;
    }
}
