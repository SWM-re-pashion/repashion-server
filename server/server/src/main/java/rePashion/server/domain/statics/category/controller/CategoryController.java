package rePashion.server.domain.statics.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.statics.category.dto.CategoryResponseDto;
import rePashion.server.domain.statics.category.dto.GetCategoriesResponseDto;
import rePashion.server.domain.statics.category.model.Category;
import rePashion.server.domain.statics.category.service.CategoryService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/v1")
    public ResponseEntity<GlobalResponse> getV1(){
        GetCategoriesResponseDto dto = categoryService.getV1();
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, dto), HttpStatus.OK);
    }

    @GetMapping("/v2")
    public ResponseEntity<GlobalResponse> getV2(){
        GetCategoriesResponseDto dto = categoryService.getV2();
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, dto), HttpStatus.OK);
    }

    @GetMapping("/v3")
    public ResponseEntity<GlobalResponse> getV3(){
        List<CategoryResponseDto> collect = categoryService.getV3()
                .stream().map(CategoryResponseDto::fromEntity)
                .collect(Collectors.toList());
        CategoryResponseDto dto = new CategoryResponseDto("성별", "gender", collect);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, dto), HttpStatus.OK);
    }

    @GetMapping("/v3/exclude")
    public ResponseEntity<GlobalResponse> getV3withExcludingEntireAndReco(){
        List<CategoryResponseDto> collect = categoryService.getV3()
                .stream().map(CategoryResponseDto::fromEntityWithNoEntireAndRecommend)
                .collect(Collectors.toList());
        CategoryResponseDto dto = new CategoryResponseDto("성별", "gender", collect);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, dto), HttpStatus.OK);
    }
}
