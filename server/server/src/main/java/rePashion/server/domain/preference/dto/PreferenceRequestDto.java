package rePashion.server.domain.preference.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import rePashion.server.domain.preference.model.BasicInfo;
import rePashion.server.domain.preference.model.Color;
import rePashion.server.domain.preference.model.ColorEntity;
import rePashion.server.domain.preference.model.Size;

import java.util.ArrayList;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferenceRequestDto {

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SelectedColor{
        private Color color;
        private ColorEntity.Ctype type;

        public SelectedColor(String color, String type) {
            this.color = Color.valueOf(color);
            this.type = ColorEntity.Ctype.valueOf(type);
        }
    }

    private BasicInfo.Gender gender;

    private String height;

    private BasicInfo.BodyShape bodyShape;

    private String topSize = "NONE";

    private String bottomSize = "NONE";

    private ArrayList<Long> styles;

    private ArrayList<SelectedColor> colors;

    public PreferenceRequestDto(String gender, String height, String bodyShape, String topSize, String bottomSize, ArrayList<Long> styles, ArrayList<SelectedColor> colors) {
        this.gender = BasicInfo.Gender.valueOf(gender);
        this.height = height;
        this.bodyShape = BasicInfo.BodyShape.valueOf(bodyShape);
        this.topSize = validateSize(topSize);
        this.bottomSize = validateSize(bottomSize);
        this.styles = styles;
        this.colors = colors;
    }

    public BasicInfo toBasicInfo(){
        return BasicInfo.builder()
                .gender(this.gender)
                .height(this.height)
                .bodyShape(this.bodyShape)
                .topSize(this.topSize)
                .bottomSize(this.bottomSize)
                .build();
    }

    private String validateSize(String size){
        String[] split = size.split("/");
        for(String s : split) Size.validate(s);
        return size;
    }
}
