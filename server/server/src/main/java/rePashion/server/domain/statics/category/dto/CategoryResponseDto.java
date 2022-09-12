package rePashion.server.domain.statics.category.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.statics.category.model.Category;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDto {

    private String id;
    private String name;
    private String code;
    private List<CategoryResponseDto> children;

    private CategoryResponseDto(String id, String name, String code, List<CategoryResponseDto> children) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.children = children;
    }

    private CategoryResponseDto(String id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public CategoryResponseDto(String name, String code, List<CategoryResponseDto> children){
        this.name = name;
        this.code = code;
        this.children = children;
    }

    public static CategoryResponseDto fromEntity(Category category){
        System.out.println(category.getCategoryId() + " " + category.getName() + " " + category.getChildren().size());
        if(category.getDepth() == 3) return new CategoryResponseDto(String.valueOf(category.getCategoryId()), category.getName(), category.getCode());
        List<CategoryResponseDto> collect = category.getChildren()
                .stream()
                .map(CategoryResponseDto::fromEntity)
                .collect(Collectors.toList());
        return new CategoryResponseDto(String.valueOf(category.getCategoryId()), category.getName(), category.getCode(), collect);
    }
}
