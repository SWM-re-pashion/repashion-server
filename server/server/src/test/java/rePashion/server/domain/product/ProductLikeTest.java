package rePashion.server.domain.product;

import org.junit.jupiter.api.Test;
import rePashion.server.domain.product.model.ProductLike;

public class ProductLikeTest {

    @Test
    public void canCreate(){
        ProductLike productLike = new ProductLike(1L, 1L, 1L);
    }
}
