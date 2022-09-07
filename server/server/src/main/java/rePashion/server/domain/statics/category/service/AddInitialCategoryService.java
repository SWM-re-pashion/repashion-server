package rePashion.server.domain.statics.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.statics.category.model.GenderCategory;
import rePashion.server.domain.statics.category.model.ParentCategory;
import rePashion.server.domain.statics.category.model.SubCategory;
import rePashion.server.domain.statics.category.repository.GenderCategoryRepository;
import rePashion.server.domain.statics.category.repository.ParentCategoryRepository;
import rePashion.server.domain.statics.category.repository.SubCategoryRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AddInitialCategoryService {

    private final GenderCategoryRepository genderCategoryRepository;
    private final ParentCategoryRepository parentCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    ArrayList<GenderCategory> genderCategories = new ArrayList<>();
    ArrayList<ParentCategory> womenParentCategory = new ArrayList<>();
    ArrayList<ParentCategory> menParentCategory = new ArrayList<>();

    public void addInitialData(){
        if(isExisted()) return;
        generateGenderCategories();
        ArrayList<ParentCategory> parentCategories1 = generateParentCategories(genderCategories.get(0));
        menParentCategory.addAll(parentCategories1);

        ArrayList<ParentCategory> parentCategories2 = generateParentCategories(genderCategories.get(1));
        womenParentCategory.addAll(parentCategories2);

        setParentCategories(menParentCategory);
        setParentCategories(womenParentCategory);
        setAdditionalParentCategories(womenParentCategory);
    }

    private boolean isExisted() {
        return !genderCategoryRepository.findAll().isEmpty();
    }
    private void generateGenderCategories() {
        genderCategories.add(new GenderCategory("남성", "men"));
        genderCategories.add(new GenderCategory("여성", "women"));
        genderCategoryRepository.saveAll(genderCategories);
    }
    private ArrayList<ParentCategory> generateParentCategories(GenderCategory genderCategory) {
        ArrayList<ParentCategory> parentCategories = new ArrayList<>();
        parentCategories.add(new ParentCategory("상의", "top"));
        parentCategories.add(new ParentCategory("하의", "bottom"));
        parentCategories.add(new ParentCategory("아우터", "outer"));
        parentCategories.forEach(pc->pc.changeGender(genderCategory));
        parentCategoryRepository.saveAll(parentCategories);
        return parentCategories;
    }

    private void setParentCategories(ArrayList<ParentCategory> parentCategories){
        ArrayList<SubCategory> subCategories1 = generateTopSubCategories();
        subCategories1.forEach(s->s.changeParent(parentCategories.get(0)));
        subCategoryRepository.saveAll(subCategories1);

        ArrayList<SubCategory> subCategories2 = generateBottomSubCategories();
        subCategories2.forEach(s->s.changeParent(parentCategories.get(1)));
        subCategoryRepository.saveAll(subCategories2);

        ArrayList<SubCategory> subCategories3 = generateOuterSubCategories();
        subCategories3.forEach(s->s.changeParent(parentCategories.get(2)));
        subCategoryRepository.saveAll(subCategories3);
    }

    private void setAdditionalParentCategories(ArrayList<ParentCategory> parentCategories){
        ArrayList<SubCategory> subCategories1 = generateAdditionalTopCategories();
        subCategories1.forEach(s->s.changeParent(parentCategories.get(0)));
        subCategoryRepository.saveAll(subCategories1);

        ArrayList<SubCategory> subCategories2 = generateAdditionalBotCategories();
        subCategories2.forEach(s->s.changeParent(parentCategories.get(1)));
        subCategoryRepository.saveAll(subCategories2);
    }

    private ArrayList<SubCategory> generateTopSubCategories(){
        ArrayList<SubCategory> topSubCategories  = new ArrayList<>();
        topSubCategories.add(new SubCategory("탑", "top"));
        topSubCategories.add(new SubCategory("반팔", "short"));
        topSubCategories.add(new SubCategory("긴팔", "long"));
        topSubCategories.add(new SubCategory("니트웨어", "knit"));
        topSubCategories.add(new SubCategory("셔츠", "shirt"));
        topSubCategories.add(new SubCategory("후드티", "hood"));
        return topSubCategories;
    }

    private ArrayList<SubCategory> generateBottomSubCategories(){
        ArrayList<SubCategory> bottomSubCategories  = new ArrayList<>();
        bottomSubCategories.add(new SubCategory("탑", "top"));
        bottomSubCategories.add(new SubCategory("반팔", "short"));
        bottomSubCategories.add(new SubCategory("긴팔", "long"));
        bottomSubCategories.add(new SubCategory("니트웨어", "knit"));
        bottomSubCategories.add(new SubCategory("셔츠", "shirt"));
        bottomSubCategories.add(new SubCategory("후드티", "hood"));
        return bottomSubCategories;
    }

    private ArrayList<SubCategory> generateOuterSubCategories(){
        ArrayList<SubCategory> outerSubCategories = new ArrayList<>();
        outerSubCategories.add(new SubCategory("코트", "coat"));
        outerSubCategories.add(new SubCategory("재킷", "jacket"));
        outerSubCategories.add(new SubCategory("점퍼", "jumper"));
        outerSubCategories.add(new SubCategory("패딩", "padding"));
        outerSubCategories.add(new SubCategory("베스트", "vest"));
        outerSubCategories.add(new SubCategory("가디건", "cardigan"));
        outerSubCategories.add(new SubCategory("집업", "zipup"));
        return outerSubCategories;
    }

    private ArrayList<SubCategory> generateAdditionalTopCategories(){
        ArrayList<SubCategory> topSubCategories = new ArrayList<>();
        topSubCategories.add(new SubCategory("블라우스", "blouse"));
        topSubCategories.add(new SubCategory("브라탑", "bratop"));
        topSubCategories.add(new SubCategory("원피스", "onepiece"));
        return topSubCategories;
    }

    private ArrayList<SubCategory> generateAdditionalBotCategories(){
        ArrayList<SubCategory> bottomCategories = new ArrayList<>();
        bottomCategories.add(new SubCategory("스커트", "skirt"));
        bottomCategories.add(new SubCategory("레깅스", "leggins"));
        return bottomCategories;
    }
}
