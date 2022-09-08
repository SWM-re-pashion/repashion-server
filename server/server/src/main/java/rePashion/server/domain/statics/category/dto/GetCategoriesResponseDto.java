package rePashion.server.domain.statics.category.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.statics.category.model.GenderCategory;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCategoriesResponseDto {

    @Getter
    public static class TopCategory{
        private String id;
        private String name;
        private String code;
        private ArrayList<MiddleCategory> children;

        public TopCategory(String id, String name, String code, ArrayList<MiddleCategory> children) {
            this.id = id;
            this.name = name;
            this.code = code;
            this.children = children;
        }
    }

    @Getter
    public static class MiddleCategory{
        private String id;
        private String name;
        private String code;
        private ArrayList<BottomCategory> children;

        public MiddleCategory(String id, String name, String code, ArrayList<BottomCategory> children) {
            this.id = id;
            this.name = name;
            this.code = code;
            this.children = children;
        }
    }

    @Getter
    public static class BottomCategory{
        private String id;
        private String name;
        private String code;

        public BottomCategory(String id, String name, String code) {
            this.id = id;
            this.name = name;
            this.code = code;
        }
    }

    private final String name = "성별";
    private final String code = "gender";
    private ArrayList<TopCategory> children = new ArrayList<>();
    public GetCategoriesResponseDto(ArrayList<TopCategory> children) {
        this.children = children;
    }

    public static GetCategoriesResponseDto fromEntity(List<GenderCategory> genderCategory){
        ArrayList<TopCategory> topCategories = new ArrayList<>();
        genderCategory.forEach((gender)->{
            ArrayList<MiddleCategory> middleCategories = new ArrayList<>();
            gender.getChildrens().forEach((parent)->{
                ArrayList<BottomCategory> bottomCategories = new ArrayList<>();
                parent.getChildrens().forEach((sub)->{
                    BottomCategory bottomCategory = new BottomCategory(String.valueOf(sub.getId()), sub.getName(), sub.getCode());
                    bottomCategories.add(bottomCategory);
                });
                MiddleCategory middleCategory = new MiddleCategory(String.valueOf(parent.getId()), parent.getName(), parent.getCode(), bottomCategories);
                middleCategories.add(middleCategory);
            });
            TopCategory topCategory = new TopCategory(String.valueOf(gender.getId()), gender.getName(), gender.getCode(), middleCategories);
            topCategories.add(topCategory);
        });
        return new GetCategoriesResponseDto(topCategories);
    }
}
