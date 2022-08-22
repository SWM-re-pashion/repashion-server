package rePashion.server.domain.preference.dto;

import lombok.*;
import rePashion.server.domain.preference.model.PreferenceBasicInfo;
import rePashion.server.domain.statics.bodyshape.BodyShape;
import rePashion.server.domain.statics.gender.Gender;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPreferenceRequestDto {

    @NotNull
    private String gender;

    @NotNull
    private int height;

    @NotNull
    private String bodyShape;

    private String topSize;
    private String bottomSize;
    private ArrayList<Long> styles;
    private String topColors;
    private String bottomColors;

    @Builder
    public PostPreferenceRequestDto(String gender, int height, String bodyShape, String topSize, String bottomSize, ArrayList<Long> styles, String topColors, String bottomColors) {
        this.gender = gender;
        this.height = height;
        this.bodyShape = bodyShape;
        this.topSize = topSize;
        this.bottomSize = bottomSize;
        this.styles = styles;
        this.topColors = topColors;
        this.bottomColors = bottomColors;
    }

    public PreferenceBasicInfo toBasicInfo(){
        return PreferenceBasicInfo.builder()
                .gender(Gender.valueOf(this.getGender()))
                .height(this.height)
                .bodyShape(BodyShape.valueOf(this.bodyShape))
                .topSize(this.topSize)
                .bottomSize(this.bottomSize)
                .topColors(this.topColors)
                .bottomColors(this.bottomColors)
                .build();
    }
}
