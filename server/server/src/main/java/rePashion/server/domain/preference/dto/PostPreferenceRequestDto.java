package rePashion.server.domain.preference.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rePashion.server.domain.preference.model.BasicInfo;
import rePashion.server.domain.preference.model.Color;
import rePashion.server.domain.preference.model.Size;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPreferenceRequestDto {

    @NotNull
    private String gender;

    @NotNull
    private String height;

    @NotNull
    private String bodyShape;

    private String topSize;
    private String bottomSize;
    private ArrayList<Long> styles;
    private String topColors;
    private String bottomColors;

    @Builder
    public PostPreferenceRequestDto(String gender, String height, String bodyShape, String topSize, String bottomSize, ArrayList<Long> styles, String topColors, String bottomColors) {
        this.gender = gender;
        this.height = height;
        this.bodyShape = bodyShape;
        this.topSize = validateSize(topSize);
        this.bottomSize = validateSize(bottomSize);
        this.styles = styles;
        this.topColors = validateColors(topColors);
        this.bottomColors = validateColors(bottomColors);
    }

    public BasicInfo toBasicInfo(){
        return BasicInfo.builder()
                .gender(BasicInfo.Gender.valueOf(this.getGender()))
                .height(this.height)
                .bodyShape(BasicInfo.BodyShape.valueOf(this.bodyShape))
                .topSize(this.topSize)
                .bottomSize(this.bottomSize)
                .topColors(this.topColors)
                .bottomColors(this.bottomColors)
                .build();
    }

    private String validateSize(String size){
        String[] split = size.split("/");
        for(String s : split) Size.validate(s);
        return size;
    }

    private String validateColors(String colors){
        String[] split = colors.split("/");
        for(String s : split) Color.valueOf(s);
        return colors;
    }
}
