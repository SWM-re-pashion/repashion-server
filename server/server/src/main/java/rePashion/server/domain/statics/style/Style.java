package rePashion.server.domain.statics.style;

import lombok.Getter;

@Getter
public enum Style {
    TRADITIONAL("트레디셔녈", "traditional"),
    MANISH("매니시", "manish"),
    FEMININE("페미닌", "feminine"),
    ETHNIC("에스닉", "ethnic"),
    CONTEMPORARY("컨템포러리", "contemporary"),
    NATURAL("내추럴", "natural"),
    GENDERLESS("젠더리스", "genderless"),
    SUBCULTURE("서브컬쳐", "subculture"),
    CASUAL("캐주얼", "casual"),
    ALL("전체", "all"),
    HIPHOP("힙합", "hiphop"),
    PUNK("펑크", "punk"),
    MODERN("모던", "modern"),
    STREET("스트리트", "street"),
    KITSCH("키치/키덜트", "kitsch"),
    SPORTY("스포티", "sporty"),
    CLASSIC("클래식", "classic"),
    RETRO("레트로", "retro"),
    AVANTGARDE("아방가르드", "avantgarde"),
    SEXY("섹시", "sexy"),
    TOMBOY("톰보이", "tomboy"),
    PREPPY("프레피", "preppy"),
    ;

    private final String name;
    private final String code;

    Style(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
