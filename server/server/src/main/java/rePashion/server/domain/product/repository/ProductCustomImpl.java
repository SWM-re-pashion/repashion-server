package rePashion.server.domain.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.product.dto.ProductDto;

@RequiredArgsConstructor
public class ProductCustomImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public ProductDto get(Long id) {
        return null;
    }

    @Override
    public ProductDetailDto getDetail(Long id) {
        return null;
    }
}
