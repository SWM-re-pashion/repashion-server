package rePashion.server.domain.product.dto;

import lombok.Data;

@Data
public class ProductRecommendBody {
    private Long productId;
    private Long associationId;
}
