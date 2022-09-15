package rePashion.server.domain.statics.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rePashion.server.domain.statics.category.model.Category;
import rePashion.server.domain.statics.category.repository.CategoryRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class InitCategoryDatabase implements CommandLineRunner {

    private ArrayList<Category> categories = new ArrayList<>();
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if(!isEmpty()) return;
        Category genderCategory1 = create(1L, "남성", "men", null);
        Category genderCategory2 = create(2L, "여성", "women", null);
        Category genderCategory3 = create(3L, "공용", "common", null);

        Category middleCategory1 = create(2001L, "상의", "top", genderCategory2);
        Category middleCategory2 = create(2002L, "하의", "bottom", genderCategory2);
        Category middleCategory3 = create(3001L, "상의", "top", genderCategory3);
        Category middleCategory4 = create(3002L, "하의", "bottom", genderCategory3);
        Category middleCategory5 = create(3003L, "아우터", "outer", genderCategory3);

        create(2001001L, "블라우스", "blouse", middleCategory1);
        create(2001002L, "브라탑", "bratop", middleCategory1);
        create(2001003L, "원피스", "onepiece", middleCategory1);

        create(2002001L, "스커트", "skirt", middleCategory2);
        create(2002002L, "레깅스", "leggings", middleCategory2);

        create(3001001L, "탑", "top", middleCategory3);
        create(3001002L, "반팔", "short", middleCategory3);
        create(3001003L, "긴팔", "long", middleCategory3);
        create(3001004L, "니트웨어", "knit", middleCategory3);
        create(3001005L, "셔츠", "shirt", middleCategory3);
        create(3001006L, "후드티", "hood", middleCategory3);

        create(3002001L, "청바지", "jeans", middleCategory4);
        create(3002002L, "팬츠", "pants", middleCategory4);
        create(3002003L, "조거팬츠", "jogger", middleCategory4);

        create(3003001L, "코트", "coat", middleCategory5);
        create(3003002L, "재킷", "jacket", middleCategory5);
        create(3003003L, "점퍼", "jumper", middleCategory5);
        create(3003004L, "패딩", "padding", middleCategory5);
        create(3003005L, "베스트", "vest", middleCategory5);
        create(3003006L, "가디건", "cardigan", middleCategory5);
        create(3003007L, "집업", "zipup", middleCategory5);
    }

    public Category create(Long id, String name,String code, Category parent){
        Category category = Category.builder()
                .categoryId(id)
                .code(code)
                .name(name)
                .build();
        category.updateParent(parent);
        categories.add(category);
        categoryRepository.save(category);
        return category;
    }

    public boolean isEmpty(){
        return categoryRepository.findAll().isEmpty();
    }
}
