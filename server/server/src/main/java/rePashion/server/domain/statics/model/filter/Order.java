package rePashion.server.domain.statics.model.filter;

public enum Order {

    LATEST("최신순", "latest"),
    LIKE("인기순", "like"),
    VIEW("최신순", "view"),
    LOW_PRICE("최신순", "low_price"),
    HIGH_PRICE("최신순", "high_price"),
    ;

    private String name;
    private String code;

    private Order(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
