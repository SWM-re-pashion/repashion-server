package rePashion.server.domain.preference.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
}
