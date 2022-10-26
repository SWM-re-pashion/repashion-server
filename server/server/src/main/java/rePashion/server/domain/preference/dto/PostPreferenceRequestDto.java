package rePashion.server.domain.preference.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
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
    private String topColors;
    private String bottomColors;

    @QueryProjection
    public PostPreferenceRequestDto(String gender, int height, String bodyShape, String topSize, String bottomSize, String topColors, String bottomColors) {
        this.gender = gender;
        this.height = height;
        this.bodyShape = bodyShape;
        this.topSize = topSize;
        this.bottomSize = bottomSize;
        this.topColors = topColors;
        this.bottomColors = bottomColors;
    }
}
