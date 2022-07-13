package rePashion.server.domain.user.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public enum Color {
    Black("Black", "#000"),
    White("White", "#fff"),
    Beige("Beige", "#e4d2c1"),
    Ivory("Ivory", "#f4edda"),
    Silver("Silver", "#e3e1e1"),
    Gray("Gray", "#959595"),
    Red("Red", "#ff5e5e"),
    Pink("Pink", "#ff61d7"),
    Orange("Orange", "#ff9635"),
    Yellow("Yellow", "#ffd747"),
    Brown("Brown", "#967556"),
    Khaki("Khaki", "#848974"),
    Green("Green", "#7db768"),
    LightGreen("Light Green", "#aae396"),
    Mint("Mint", "#acfbdf"),
    Blue("Blue", "#4bb4ff"),
    Navy("Navy", "#38466c"),
    Purple("Purple", "#936dff");

    private final String name;
    private final String code;

    Color(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static ArrayList<Color> getAll() {
        return new ArrayList<>(Arrays.asList(Color.values()));
    }
}
