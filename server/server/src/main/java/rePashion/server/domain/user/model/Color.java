package rePashion.server.domain.user.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;


@Getter
public enum Color {
    BLACK("Black", "000000"),
    WHITE("White", "FFFFFF"),
    GREY("Gray", "666666"),
    RED("Red","FF0000"),
    PINK("Pink", "FF00CC"),
    ORANGE("Orange", "FF6600"),
    BEIGE("Beige", "FF9933"),
    BROWN("Brown", "993300"),
    YELLOW("Yellow", "FFCC00"),
    GREEN("Green", "009900"),
    LIGHTGREEN("Light Green", "33CC33"),
    KHAKI("Khaki", "336633"),
    SILVER("Silver", "999999"),
    NAVY("Navy", "003366"),
    IVORY("Ivory", "FFCC99"),
    BLUE("Blue", "3300FF"),
    MINT("Mint", "00FFFF"),
    PURPLE("Purple", "663399");

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
