package rePashion.server.domain.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ProductPreviewDto {

    private Long id;
    private String img;
    private String title;
    private String size;
    private Integer like;
    private Integer price;
    private Boolean isSoldOut;
    private String updatedAt;

    @QueryProjection
    public ProductPreviewDto(Long id, String img, String title, String size, Integer like, Integer price, Boolean isSoldOut, LocalDateTime updatedAt) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.size = size;
        this.like = like;
        this.price = price;
        this.isSoldOut = isSoldOut;
        this.updatedAt = updatedAt.format(DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm:ss"));
    }
}
