package rePashion.server.domain.statics.gender;

import lombok.Getter;

@Getter
public enum Gender {

    MEN("남성", "men"),
    WOMEN("여성","women")
    ;

    private String name;
    private String code;

    Gender(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
