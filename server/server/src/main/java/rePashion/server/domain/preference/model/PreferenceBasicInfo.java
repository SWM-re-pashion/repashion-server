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
        this.gender = check(gender);
        this.height = height;
        this.bodyShape = check(bodyShape);
        this.topSize = splitAndCheck(topSize);
        this.bottomSize = splitAndCheck(bottomSize);
        this.topColors = splitAndCheck(topColors);
        this.bottomColors = splitAndCheck(bottomColors);
    }

    public PreferenceBasicInfo(PostPreferenceRequestDto dto){
        this.gender = check(dto.getGender());
        this.height = dto.getHeight();
        this.bodyShape = check(dto.getBodyShape());
        this.topSize = splitAndCheck(dto.getTopSize());
        this.bottomSize = splitAndCheck(dto.getBottomSize());
        this.topColors = splitAndCheck(dto.getTopColors());
        this.bottomColors = splitAndCheck(dto.getBottomColors());
    }

    public String check(String staticValue){
        boolean contain = StaticsService.lookups.containsKey(staticValue);
        if(!contain) throw new StaticVariableNotExisted(ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
        return staticValue;
    }

    public String splitAndCheck(String staticValue){
        String[] splitValues = staticValue.split("/");
        Arrays.stream(splitValues).forEach(value->{
            boolean contain = StaticsService.lookups.containsKey(value);
            if(!contain) throw new StaticVariableNotExisted(ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
        });
        return staticValue;
    }
}
