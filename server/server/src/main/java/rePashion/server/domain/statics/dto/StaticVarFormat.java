package rePashion.server.domain.statics.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StaticVarFormat {
    String name;
    String code;

    public StaticVarFormat(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
