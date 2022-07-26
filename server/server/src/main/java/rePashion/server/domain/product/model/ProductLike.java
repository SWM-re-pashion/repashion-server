package rePashion.server.domain.product.model;

import lombok.Builder;

public class ProductLike {
    private Long id;
    private Long ProductId;
    private Long UserId;

    public ProductLike(Long id, Long productId, Long userId) {
        this.id = id;
        ProductId = productId;
        UserId = userId;
    }
}
