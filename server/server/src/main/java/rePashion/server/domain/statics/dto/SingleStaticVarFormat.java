package rePashion.server.domain.statics.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleStaticVarFormat {

    private String name;

    public SingleStaticVarFormat(String name) {
        this.name = name;
    }
}
