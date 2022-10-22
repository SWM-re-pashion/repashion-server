package rePashion.server.domain.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CategoryDto {
    private String genderCategory;
    private String parentCategory;
    private String subCategory;

    @QueryProjection
    public CategoryDto(String genderCategory, String parentCategory, String subCategory) {
        this.genderCategory = genderCategory;
        this.parentCategory = parentCategory;
        this.subCategory = subCategory;
    }
}
