package rePashion.server.domain.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import rePashion.server.domain.product.model.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
        this.updatedAt = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static ProductPreviewDto fromEntity(Product product) {
        return new ProductPreviewDto(
                product.getId(),
                product.getBasicInfo().getThumbnailImage(),
                product.getBasicInfo().getTitle(),
                product.getBasicInfo().getSize(),
                product.getBasicInfo().getLikes(),
                product.getBasicInfo().getPrice(),
                product.getBasicInfo().getStatus(),
                product.getModifiedDate()
                );
    }
}
