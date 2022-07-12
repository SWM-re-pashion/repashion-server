package rePashion.server.domain.user.model;

import lombok.Getter;

@Getter
public enum Size {

    TOP_XS("TOP", "XS"),
    TOP_S("TOP", "S"),
    TOP_M("TOP", "M"),
    TOP_L("TOP", "L"),
    TOP_XL("TOP", "XL"),
    TOP_2XL("TOP", "2XL"),
    TOP_3XL("TOP", "3XL"),
    TOP_FREE("TOP", "FREE"),
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
    BOT_37("BOTTOM", "37");

    private final String type;
    private final String size;

    Size(String type, String size) {
        this.type = type;
        this.size = size;
    }
}
