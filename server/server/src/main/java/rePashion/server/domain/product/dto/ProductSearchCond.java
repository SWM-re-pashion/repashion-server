package rePashion.server.domain.product.dto;

import lombok.Data;
import rePashion.server.domain.statics.model.filter.Order;

@Data
public class ProductSearchCond {
    private String category;
    private Order order;
    private Boolean hideSold;

    public ProductSearchCond(String category, String order, Boolean hideSold) {
        this.category = category;
        this.order = Order.valueOf(order);
        this.hideSold = hideSold;
    }
}
