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
    private final AddBasicCategoryService addBasicCategoryService;

    public GetCategoriesResponseDto get(){
        addBasicCategoryService.addBasicVariablse();
        List<GenderCategory> genderCategories = genderCategoryRepository.findGenderCategories();
        GetCategoriesResponseDto getCategoriesResponseDto = GetCategoriesResponseDto.fromEntity(genderCategories);
        return getCategoriesResponseDto;
    }
}
