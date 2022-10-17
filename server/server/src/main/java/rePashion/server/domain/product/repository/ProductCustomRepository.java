package rePashion.server.domain.product.repository;

import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.product.dto.ProductDto;

public interface ProductCustomRepository {

    ProductDto get(Long id);

    ProductDetailDto getDetail(Long id);
}
