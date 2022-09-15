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

        create(1999L, "추천", "recommend", genderCategory1);
        create(1000L, "전체", "all", genderCategory1);
        Category middleCategory1 = create(1001L, "상의", "top", genderCategory1);
        Category middleCategory2 = create(1002L, "하의", "bottom", genderCategory1);
        Category middleCategory3 = create(1003L, "아우터", "outer", genderCategory1);

        create(2999L, "추천", "recommend", genderCategory2);
        create(2000L, "전체", "all", genderCategory2);
        Category middleCategory4 = create(2001L, "상의", "top", genderCategory2);
        Category middleCategory5 = create(2002L, "하의", "bottom", genderCategory2);
        Category middleCategory6 = create(2003L, "아우터", "outer", genderCategory2);
        Category middleCategory7 = create(2004L, "원피스", "outer", genderCategory2);

        create(3999L, "추천", "recommend", genderCategory3);
        create(3000L, "전체", "all", genderCategory3);
        Category middleCategory8 = create(3001L, "상의", "top", genderCategory3);
        Category middleCategory9 = create(3002L, "하의", "bottom", genderCategory3);
        Category middleCategory10 = create(3003L, "아우터", "outer", genderCategory3);

        create(1001000L, "전체", "all", middleCategory1);
        create(1001001L, "탑", "top", middleCategory1);
        create(1001002L, "반팔", "short", middleCategory1);
        create(1001003L, "긴팔", "long", middleCategory1);
        create(1001004L, "니트웨어", "knit", middleCategory1);
        create(1001005L, "셔츠", "shirt", middleCategory1);
        create(1001006L, "후드티", "hood", middleCategory1);

        create(1002000L, "전체", "all", middleCategory2);
        create(1002001L, "청바지", "jeans", middleCategory2);
        create(1002002L, "팬츠", "pants", middleCategory2);
        create(1002003L, "조거팬츠", "jogger", middleCategory2);

        create(1003000L, "전체", "all", middleCategory3);
        create(1003001L, "코트", "coat", middleCategory3);
        create(1003002L, "재킷", "jacket", middleCategory3);
        create(1003003L, "점퍼", "jumper", middleCategory3);
        create(1003004L, "패딩", "padding", middleCategory3);
        create(1003005L, "베스트", "vest", middleCategory3);
        create(1003006L, "가디건", "cardigan", middleCategory3);
        create(1003007L, "집업", "zipup", middleCategory3);

        create(2001000L, "전체", "all", middleCategory4);
        create(2001001L, "탑", "top", middleCategory4);
        create(2001002L, "반팔", "short", middleCategory4);
        create(2001003L, "긴팔", "long", middleCategory4);
        create(2001004L, "니트웨어", "knit", middleCategory4);
        create(2001005L, "셔츠", "shirt", middleCategory4);
        create(2001006L, "후드티", "hood", middleCategory4);
        create(2001007L, "블라우스", "blouse", middleCategory4);
        create(2001008L, "브라탑", "bratop", middleCategory4);

        create(2002000L, "전체", "all", middleCategory5);
        create(2002001L, "청바지", "jeans", middleCategory5);
        create(2002002L, "팬츠", "pants", middleCategory5);
        create(2002003L, "조거팬츠", "jogger", middleCategory5);
        create(2002004L, "스커트", "skirt", middleCategory5);
        create(2002005L, "레깅스", "leggings", middleCategory5);

        create(2003000L, "전체", "all", middleCategory6);
        create(2003001L, "코트", "coat", middleCategory6);
        create(2003002L, "재킷", "jacket", middleCategory6);
        create(2003003L, "점퍼", "jumper", middleCategory6);
        create(2003004L, "패딩", "padding", middleCategory6);
        create(2003005L, "베스트", "vest", middleCategory6);
        create(2003006L, "가디건", "cardigan", middleCategory6);
        create(2003007L, "집업", "zipup", middleCategory6);

        create(2004000L, "전체", "all", middleCategory7);
        create(2004001L, "드레스", "dress", middleCategory7);
        create(2004002L, "점프수트", "jumpsuit", middleCategory7);

        create(3001000L, "전체", "all", middleCategory8);
        create(3001001L, "탑", "top", middleCategory8);
        create(3001002L, "반팔", "short", middleCategory8);
        create(3001003L, "긴팔", "long", middleCategory8);
        create(3001004L, "니트웨어", "knit", middleCategory8);
        create(3001005L, "셔츠", "shirt", middleCategory8);
        create(3001006L, "후드티", "hood", middleCategory8);

        create(3002000L, "전체", "all", middleCategory9);
        create(3002001L, "청바지", "jeans", middleCategory9);
        create(3002002L, "팬츠", "pants", middleCategory9);
        create(3002003L, "조거팬츠", "jogger", middleCategory9);

        create(3003001L, "코트", "coat", middleCategory10);
        create(3003002L, "재킷", "jacket", middleCategory10);
        create(3003003L, "점퍼", "jumper", middleCategory10);
        create(3003004L, "패딩", "padding", middleCategory10);
        create(3003005L, "베스트", "vest", middleCategory10);
        create(3003006L, "가디건", "cardigan", middleCategory10);
        create(3003007L, "집업", "zipup", middleCategory10);
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
