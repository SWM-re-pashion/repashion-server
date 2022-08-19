package rePashion.server.domain.statics.length;

import lombok.Getter;

@Getter
public enum TopLength {

    all("전체","all"),
    crop("크롭","crop"),
    waist("허리","waist"),
    pelvis("골반","pelvis"),
    hip("엉덩이","hip"),
    thigh("허벅지","thigh"),
    shin("정강이","shin"),
    ankle("발목","ankle")
    ;

    private final String name;
    private final String code;

    TopLength(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
