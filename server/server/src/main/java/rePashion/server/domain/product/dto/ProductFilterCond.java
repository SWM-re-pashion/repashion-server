package rePashion.server.domain.product.dto;

import lombok.Data;
import rePashion.server.domain.statics.model.filter.Order;

import java.util.List;

@Data
public class ProductFilterCond {
    private String category;
    private List<String> style;
    private List<String> color;
    private List<String> fit;
    private List<String> length;
    private List<String> clothesSize;
    private Order order;
    private Boolean hideSold;
    private Integer priceLoe;
    private Integer priceGoe;
}
