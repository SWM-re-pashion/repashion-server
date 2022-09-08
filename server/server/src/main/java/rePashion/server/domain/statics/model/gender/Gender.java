package rePashion.server.domain.statics.model.gender;

import lombok.Getter;

@Getter
public enum Gender {

    men("남성", "men"),
    women("여성","women")
    ;

    private String name;
    private String code;

    Gender(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
