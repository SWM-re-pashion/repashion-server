package rePashion.server.domain.statics.length;

import lombok.Getter;

@Getter
public enum TopLength {

    ALL("전체","all"),
    CROP("크롭","crop"),
    WAIST("허리","waist"),
    PELVIS("골반","pelvis"),
    HIP("엉덩이","hip"),
    THIGH("허벅지","thigh"),
    SHIN("정강이","shin"),
    ANKLE("발목","ankle")
    ;

    private final String name;
    private final String code;

    TopLength(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
