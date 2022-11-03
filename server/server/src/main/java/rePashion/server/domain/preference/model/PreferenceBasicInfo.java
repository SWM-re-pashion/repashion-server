package rePashion.server.domain.preference.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.preference.dto.PostPreferenceRequestDto;
import rePashion.server.domain.statics.exception.StaticVariableNotExisted;
import rePashion.server.domain.statics.service.StaticsService;
import rePashion.server.global.error.exception.ErrorCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Arrays;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferenceBasicInfo {

    @Column(nullable = false)
    String gender;

    @Column(nullable = false)
    int height;

    @Column(nullable = false)
    String bodyShape;

    String topSize;

    String bottomSize;

    String topColors;

    String bottomColors;

    @Builder
    public PreferenceBasicInfo(String gender, int height, String bodyShape, String topSize, String bottomSize, String topColors, String bottomColors) {
        this.gender = check("Gender",gender);
        this.height = height;
        this.bodyShape = check("BodyShape",bodyShape);
        this.topSize = splitAndCheck("Size",topSize);
        this.bottomSize = splitAndCheck("Size",bottomSize);
        this.topColors = topColors;
        this.bottomColors = bottomColors;
    }

    public PreferenceBasicInfo(PostPreferenceRequestDto dto){
        this.gender = check("Gender",dto.getGender());
        this.height = dto.getHeight();
        this.bodyShape = check("BodyShape",dto.getBodyShape());
        this.topSize = splitAndCheck("Size",dto.getTopSize());
        this.bottomSize = splitAndCheck("Size",dto.getBottomSize());
        this.topColors = dto.getTopColors();
        this.bottomColors = dto.getBottomColors();
    }

    public String check(String type, String staticValue){
        if(staticValue.equals("")) return null;
        boolean contain = StaticsService.lookups.containsKey(type+staticValue);
        if(!contain) throw new StaticVariableNotExisted(ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
        return staticValue;
    }

    public String splitAndCheck(String type, String staticValue){
        if(staticValue.equals("")) return null;
        String[] splitValues = staticValue.split("/");
        Arrays.stream(splitValues).forEach(value->{
            boolean contain = StaticsService.lookups.containsKey(type+value);
            if(!contain) throw new StaticVariableNotExisted(ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
        });
        return staticValue;
    }
}
