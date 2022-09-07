package rePashion.server.domain.statics.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.statics.category.dto.GetCategoriesResponseDto;
import rePashion.server.domain.statics.category.model.GenderCategory;
import rePashion.server.domain.statics.category.repository.GenderCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final GenderCategoryRepository genderCategoryRepository;
    private final AddInitialCategoryService addBasicCategoryService;

    public GetCategoriesResponseDto getV1(){
        addBasicCategoryService.addInitialData();
        List<GenderCategory> genderCategories = genderCategoryRepository.findAllCategoriesV1();
        GetCategoriesResponseDto getCategoriesResponseDto = GetCategoriesResponseDto.fromEntity(genderCategories);
        return getCategoriesResponseDto;
    }

    public GetCategoriesResponseDto getV2(){
        addBasicCategoryService.addInitialData();
        List<GenderCategory> genderCategories = genderCategoryRepository.findAllCategoriesV2();
        GetCategoriesResponseDto getCategoriesResponseDto = GetCategoriesResponseDto.fromEntity(genderCategories);
        return getCategoriesResponseDto;
    }
}
