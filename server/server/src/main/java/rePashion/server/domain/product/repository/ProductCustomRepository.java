package rePashion.server.domain.product.repository;

import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.product.dto.ProductRequestBody;

public interface ProductCustomRepository {

    ProductRequestBody get(Long id);

    ProductDetailDto getDetail(Long id);
}
