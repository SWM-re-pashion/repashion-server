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
        private String name;
        private String code;
        private ArrayList<MiddleCategory> children;

        public TopCategory(String name, String code, ArrayList<MiddleCategory> children) {
            this.name = name;
            this.code = code;
            this.children = children;
        }
    }

    @Getter
    public static class MiddleCategory{
        private String name;
        private String code;
        private ArrayList<BottomCategory> children;

        public MiddleCategory(String name, String code, ArrayList<BottomCategory> children) {
            this.name = name;
            this.code = code;
            this.children = children;
        }
    }

    @Getter
    public static class BottomCategory{
        private String name;
        private String code;

        public BottomCategory(String name, String code) {
            this.name = name;
            this.code = code;
        }
    }

    private ArrayList<TopCategory> children = new ArrayList<>();
    private final String name = "성별";
    private final String code = "gender";

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
                    BottomCategory bottomCategory = new BottomCategory(sub.getName(), sub.getCode());
                    bottomCategories.add(bottomCategory);
                });
                MiddleCategory middleCategory = new MiddleCategory(parent.getName(), parent.getCode(), bottomCategories);
                middleCategories.add(middleCategory);
            });
            TopCategory topCategory = new TopCategory(gender.getName(), gender.getCode(), middleCategories);
            topCategories.add(topCategory);
        });
        return new GetCategoriesResponseDto(topCategories);
    }
}
