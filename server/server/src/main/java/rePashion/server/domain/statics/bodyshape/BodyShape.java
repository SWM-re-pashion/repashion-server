package rePashion.server.domain.statics.bodyshape;

import lombok.Getter;

@Getter
public enum BodyShape {
    THIN("마름", "thin"),
    NORMAL("보통", "normal"),
    CHUBBY("통통", "chubby"),
    FAT("뚱뚱", "fat")
    ;
    private String name;
    private String code;

    BodyShape(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
