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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
        if(category.getDepth() == 3) return new CategoryResponseDto(String.valueOf(category.getCategoryId()), category.getName(), category.getCode());
        List<CategoryResponseDto> collect = category.getChildren()
                .stream()
                .map(CategoryResponseDto::fromEntity)
                .collect(Collectors.toList());
        return new CategoryResponseDto(String.valueOf(category.getCategoryId()), category.getName(), category.getCode(), collect);
    }

    public static CategoryResponseDto fromEntityWithNoEntireAndRecommend(Category category){
        if(category.getDepth() == 3) return new CategoryResponseDto(String.valueOf(category.getCategoryId()), category.getName(), category.getCode());
        List<CategoryResponseDto> collect = category.getChildren()
            .stream()
            .filter(o -> !o.getName().equals("전체") && !o.getName().equals("추천"))
            .map(CategoryResponseDto::fromEntityWithNoEntireAndRecommend)
            .collect(Collectors.toList());
        return new CategoryResponseDto(String.valueOf(category.getCategoryId()), category.getName(), category.getCode(), collect);
    }
}
