package rePashion.server.domain.statics.model.color;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum BottomColor {

    BLACK("Black","#000"),
    WHITE("White","#fff"),
    BEIGE("Beige","#e4d2c1"),
    IVORY("Ivory","#f4edda"),
    SILVER("Silver","#e3e1e1"),
    GRAY("Gray","#959595"),
    RED("Red","#ff5e5e"),
    PINK("Pink","#ff61d7"),
    ORANGE("Orange","#ff9635"),
    YELLOW("Yellow","#ffd747"),
    BROWN("Brown","#967556"),
    KHAKI("Khaki","#848974"),
    GREEN("Green","#7db768"),
    LIGHTGREEN("Light Green","#aae396"),
    MINT("Mint","#acfbdf"),
    BLUE("Blue","#4bb4ff"),
    NAVY("Navy","#38466c"),
    PURPLE("Purple","#936dff");

    private final String name;
    private final String code;

    BottomColor(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public HashMap<String, String> getAllNamesByMap() {
        HashMap<String, String> colors = new HashMap<>();
        for (BottomColor color : BottomColor.values())
            colors.put(color.getName(), String.valueOf(color));
        return colors;
    }
}
