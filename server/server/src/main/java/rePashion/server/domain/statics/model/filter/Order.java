package rePashion.server.domain.statics.model.filter;

public enum Order {

    latest("최신순", "latest"),
    like("인기순", "like"),
    view("최신순", "view"),
    low_price("낮은 가격순", "low price"),
    high_price("높은 가격순", "high price"),
    ;

    private String name;
    private String code;

    private Order(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
