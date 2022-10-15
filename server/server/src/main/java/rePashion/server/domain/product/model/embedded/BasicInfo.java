package rePashion.server.domain.product.model.embedded;

import com.querydsl.core.annotations.QueryProjection;
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

    private int views = 0;

    private int likes = 0;

    private Boolean status = false;

    @Builder
    public BasicInfo(String title, String contact, String category, String brand, String thumbnailImage, int price, boolean isIncludeDelivery, String size) {
        this.title = title;
        this.contact = contact;
        this.category = category;
        this.brand = brand;
        this.thumbnailImage = thumbnailImage;
        this.price = price;
        this.isIncludeDelivery = isIncludeDelivery;
        this.size = size;
    }

    public void changeThumbNail(String thumbnailImage){
        this.thumbnailImage = thumbnailImage;
    }

    public void increaseLikes(){
        this.likes++;
    }

    public void decreaseLikes(){
        this.likes--;
    }

    public void increaseViews(){
        this.views++;
    }

    public void decreaseViews(){
        this.views--;
    }

    public void changeStatus(){
        this.status = !status;
    }
}
