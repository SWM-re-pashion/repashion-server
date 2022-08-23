package rePashion.server.domain.statics.bodyshape;

import lombok.Getter;

@Getter
public enum BodyShape {
    thin("마름", "thin"),
    normal("보통", "normal"),
    chubby("통통", "chubby"),
    fat("뚱뚱", "fat")
    ;
    private String name;
    private String code;

    BodyShape(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
