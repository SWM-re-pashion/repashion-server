package rePashion.server.domain.product.resources.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import rePashion.server.domain.statics.model.filter.Order;

public class Condition {

    @Data
    public static class SearchCond{
        private String value;
        private Order order;

        @JsonProperty("hide_sold")
        private Boolean hideSold;
    }

    @Data
    public static class Filter{
        private String category;
        private String style;
        private String color;
        private String fit;
        private String length;

        @JsonProperty("clothes_size")
        private String clothesSize;

        private Order order;

        @JsonProperty("hide_sold")
        private Boolean hideSold;

        @JsonProperty("price_loe")
        private Integer priceLoe;

        @JsonProperty("price_goe")
        private Integer priceGoe;
    }
}
