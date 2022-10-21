package rePashion.server.domain.product.repository;

import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.product.dto.ProductDto;
import rePashion.server.domain.user.model.User;

public interface ProductCustomRepository {

    ProductDto get(Long id);

    ProductDetailDto getDetail(User user, Long id);
}
