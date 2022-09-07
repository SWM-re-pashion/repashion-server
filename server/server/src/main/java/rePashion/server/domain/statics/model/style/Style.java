package rePashion.server.domain.statics.model.style;

import lombok.Getter;

@Getter
public enum Style {
    traditional("트레디셔녈", "traditional"),
    manish("매니시", "manish"),
    feminine("페미닌", "feminine"),
    ethnic("에스닉", "ethnic"),
    contemporary("컨템포러리", "contemporary"),
    natural("내추럴", "natural"),
    genderless("젠더리스", "genderless"),
    subculture("서브컬쳐", "subculture"),
    casual("캐주얼", "casual"),
    all("전체", "all"),
    hiphop("힙합", "hiphop"),
    punk("펑크", "punk"),
    modern("모던", "modern"),
    street("스트리트", "street"),
    kitsch("키치/키덜트", "kitsch"),
    sporty("스포티", "sporty"),
    classic("클래식", "classic"),
    retro("레트로", "retro"),
    avantgarde("아방가르드", "avantgarde"),
    sexy("섹시", "sexy"),
    tomboy("톰보이", "tomboy"),
    preppy("프레피", "preppy"),
    ;

    private final String name;
    private final String code;

    Style(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
