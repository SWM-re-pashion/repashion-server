package rePashion.server.domain.user.model;

import lombok.Getter;
import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

@Getter
public enum Size {

    XS("TOP", "XS"),
    S("TOP", "S"),
    M("TOP", "M"),
    L("TOP", "L"),
    XL("TOP", "XL"),
    XXL("TOP", "2XL"),
    XXXL("TOP", "3XL"),
    FREE("TOP", "FREE"),
    BOT_22("BOTTOM", "22"),
    BOT_23("BOTTOM", "23"),
    BOT_24("BOTTOM", "24"),
    BOT_25("BOTTOM", "25"),
    BOT_26("BOTTOM", "26"),
    BOT_27("BOTTOM", "27"),
    BOT_28("BOTTOM", "28"),
    BOT_29("BOTTOM", "29"),
    BOT_30("BOTTOM", "30"),
    BOT_31("BOTTOM", "31"),
    BOT_32("BOTTOM", "32"),
    BOT_33("BOTTOM", "33"),
    BOT_34("BOTTOM", "34"),
    BOT_35("BOTTOM", "35"),
    BOT_36("BOTTOM", "36"),
    BOT_37("BOTTOM", "37"),
    NONE("NONE", "0");

    private final String type;
    private final String size;

    Size(String type, String size) {
        this.type = type;
        this.size = size;
    }

    public static void validate(String s){
        for(Size size : Size.values()) if(size.getSize().equals(s)) return;
        throw new InvalidValueException(ErrorCode.SIZE_NOT_EXISTED);
    }
}
