package rePashion.server.domain.statics.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.statics.category.model.GenderCategory;
import rePashion.server.domain.statics.category.model.ParentCategory;
import rePashion.server.domain.statics.category.model.SubCategory;
import rePashion.server.domain.statics.category.repository.GenderCategoryRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AddBasicCategoryService {

    private final GenderCategoryRepository genderCategoryRepository;

    private ParentCategory topParentCategory;
    private ParentCategory bottomParentCategory;
    private ParentCategory outerParentCategory;
    static boolean status = false;

    public void addBasicVariablse(){
        if(status) return;
        setParentCategory();
        addMale();
        addFemale();
        addCommon();
        status = true;
    }

    private void setParentCategory(){
        topParentCategory = new ParentCategory("상의", "top");
        bottomParentCategory = new ParentCategory("하의", "bottom");
        outerParentCategory = new ParentCategory("아우터", "outer");
    }

    private void addCommon() {
        ArrayList<SubCategory> topSubCategories  = new ArrayList<>();
        topSubCategories.add(new SubCategory("탑", "top"));
        topSubCategories.add(new SubCategory("반팔", "short"));
        topSubCategories.add(new SubCategory("긴팔", "long"));
        topSubCategories.add(new SubCategory("니트웨어", "knit"));
        topSubCategories.add(new SubCategory("셔츠", "shirt"));
        topSubCategories.add(new SubCategory("후드티", "hood"));

        ArrayList<SubCategory> bottomCategories = new ArrayList<>();
        bottomCategories.add(new SubCategory("청바지", "jeans"));
        bottomCategories.add(new SubCategory("팬츠", "pants"));
        bottomCategories.add(new SubCategory("조거팬츠", "jogger"));

        ArrayList<SubCategory> outerSubCategories = new ArrayList<>();
        outerSubCategories.add(new SubCategory("코트", "coat"));
        outerSubCategories.add(new SubCategory("재킷", "jacket"));
        outerSubCategories.add(new SubCategory("점퍼", "jumper"));
        outerSubCategories.add(new SubCategory("패딩", "padding"));
        outerSubCategories.add(new SubCategory("베스트", "vest"));
        outerSubCategories.add(new SubCategory("가디건", "cardigan"));
        outerSubCategories.add(new SubCategory("집업", "zipup"));

        topSubCategories.forEach((e) -> e.changeParent(topParentCategory));
        bottomCategories.forEach((e) -> e.changeParent(bottomParentCategory));
        outerSubCategories.forEach((e) -> e.changeParent(outerParentCategory));

        GenderCategory genderCategory = new GenderCategory("공용", "common");
        topParentCategory.changeGender(genderCategory);
        bottomParentCategory.changeGender(genderCategory);
        outerParentCategory.changeGender(genderCategory);
        genderCategoryRepository.save(genderCategory);
    }

    private void addFemale() {
        ArrayList<SubCategory> topSubCategories = new ArrayList<>();
        topSubCategories.add(new SubCategory("블라우스", "blouse"));
        topSubCategories.add(new SubCategory("브라탑", "bratop"));
        topSubCategories.add(new SubCategory("원피스", "onepiece"));

        ArrayList<SubCategory> bottomCategories = new ArrayList<>();
        bottomCategories.add(new SubCategory("스커트", "skirt"));
        bottomCategories.add(new SubCategory("레깅스", "leggins"));

        topSubCategories.forEach((e) -> e.changeParent(topParentCategory));
        bottomCategories.forEach((e) -> e.changeParent(bottomParentCategory));

        GenderCategory genderCategory = new GenderCategory("여성", "women");
        topParentCategory.changeGender(genderCategory);
        bottomParentCategory.changeGender(genderCategory);
        genderCategoryRepository.save(genderCategory);
    }

    private void addMale() {
        SubCategory subCategory = new SubCategory("민소매", "sleevelss");
        subCategory.changeParent(topParentCategory);
        GenderCategory genderCategory = new GenderCategory("남성", "men");
        topParentCategory.changeGender(genderCategory);

        genderCategoryRepository.save(genderCategory);
    }
}
