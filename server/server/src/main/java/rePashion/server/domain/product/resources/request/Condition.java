package rePashion.server.domain.product.resources.request;

import lombok.Data;
import rePashion.server.domain.statics.model.filter.Order;

public class Condition {

    @Data
    public static class SearchCond{
        private String value;
        private Order order;
        private Boolean hideSold;
    }

    @Data
    public static class Filter{
        private String category;
        private String style;
        private String color;
        private String fit;
        private String length;
        private String clothesSize;
        private Order order;
        private Boolean hideSold;
        private Integer priceLoe;
        private Integer priceGoe;
    }
}
