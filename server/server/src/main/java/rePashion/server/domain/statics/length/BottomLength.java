package rePashion.server.domain.statics.length;

import lombok.Getter;

@Getter
public enum BottomLength {

    ALL("전체","all"),
    MINI("미니","mini"),
    THIGH("허벅지","thigh"),
    KNEE("무릎","knee"),
    SHIN("정강이","shin"),
    ANKLE("발목","ankle"),
    FOOT("발","foot"),
    ;

    private final String name;
    private final String code;

    BottomLength(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
