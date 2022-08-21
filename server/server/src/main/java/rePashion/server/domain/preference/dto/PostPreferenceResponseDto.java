package rePashion.server.domain.preference.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import rePashion.server.domain.preference.model.Preference;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPreferenceResponseDto {
    private int status;
    private String style;

    private PostPreferenceResponseDto(int status, String style) {
        this.status = status;
        this.style = style;
    }

    public static PostPreferenceResponseDto of(Preference preference){
        return new PostPreferenceResponseDto(200, "합합");
    }
}
