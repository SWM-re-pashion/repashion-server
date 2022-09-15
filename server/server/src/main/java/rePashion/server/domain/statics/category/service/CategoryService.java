package rePashion.server.domain.statics.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.statics.category.dto.CategoryResponseDto;
import rePashion.server.domain.statics.category.dto.GetCategoriesResponseDto;
import rePashion.server.domain.statics.category.model.Category;
import rePashion.server.domain.statics.category.model.GenderCategory;
import rePashion.server.domain.statics.category.model.ParentCategory;
import rePashion.server.domain.statics.category.model.SubCategory;
import rePashion.server.domain.statics.category.repository.CategoryRepository;
import rePashion.server.domain.statics.category.repository.GenderCategoryRepository;
import rePashion.server.domain.statics.category.repository.ParentCategoryRepository;
import rePashion.server.domain.statics.category.repository.SubCategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final GenderCategoryRepository genderCategoryRepository;
    private final ParentCategoryRepository parentCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final InitCategoryService initCategoryService;

    public GetCategoriesResponseDto getV1(){
        initCategoryService.initData();
        List<GenderCategory> genderCategories = genderCategoryRepository.findAllCategoriesV1();
        GetCategoriesResponseDto getCategoriesResponseDto = GetCategoriesResponseDto.fromEntity(genderCategories);
        return getCategoriesResponseDto;
    }

    public GetCategoriesResponseDto getV2(){
        initCategoryService.initData();
        List<GenderCategory> genderCategories = genderCategoryRepository.findAllGenderCategories();
        //genderCategories.forEach(g->parentCategoryRepository.findAllParentCategoriesById(g.getId()).forEach(s->subCategoryRepository.findAllSubCategoriesById(s.getId())));
        GetCategoriesResponseDto getCategoriesResponseDto = GetCategoriesResponseDto.fromEntity(genderCategories);
        return getCategoriesResponseDto;
    }

    public List<Category> getV3(){
        return categoryRepository.findTopCategories();
    }
}
