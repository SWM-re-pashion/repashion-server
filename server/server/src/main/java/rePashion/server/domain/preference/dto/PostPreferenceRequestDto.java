package rePashion.server.domain.preference.dto;

import lombok.*;
import rePashion.server.domain.preference.model.PreferenceBasicInfo;
import rePashion.server.domain.statics.model.bodyshape.BodyShape;
import rePashion.server.domain.statics.model.gender.Gender;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
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
