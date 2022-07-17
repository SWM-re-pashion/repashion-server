package rePashion.server.domain.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPrefenceRequestDto {
    private String gender;
    private String height;
    private String bodyShape;
    private String topSize;
    private String bottomSize;
    private ArrayList<Long> styles;
    private ArrayList<String> topColors;
    private ArrayList<String> bottomColors;

    public PostPrefenceRequestDto(String gender, String height, String bodyShape, String topSize, String bottomSize, ArrayList<Long> styles, ArrayList<String> topColors, ArrayList<String> bottomColors) {
        this.gender = gender;
        this.height = height;
        this.bodyShape = bodyShape;
        this.topSize = topSize;
        this.bottomSize = bottomSize;
        this.styles = styles;
        this.topColors = topColors;
        this.bottomColors = bottomColors;
    }
}
