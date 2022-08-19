package rePashion.server.domain.statics.length;

import lombok.Getter;

@Getter
public enum BottomLength {

    all("전체","all"),
    mini("미니","mini"),
    thigh("허벅지","thigh"),
    knee("무릎","knee"),
    shin("정강이","shin"),
    ankle("발목","ankle"),
    foot("발","foot"),
    ;

    private final String name;
    private final String code;

    BottomLength(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
