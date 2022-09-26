package rePashion.server.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rePashion.server.domain.product.dto.ProductFilterCond;
import rePashion.server.domain.product.dto.ProductPreviewDto;
import rePashion.server.domain.product.dto.ProductSearchCond;
import rePashion.server.domain.product.dto.response.ShopResponseDto;
import rePashion.server.domain.product.repository.ProductFilterRepository;
import rePashion.server.domain.product.repository.ProductSearchRepository;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ProductSearchRepository productSearchRepository;
    private final ProductFilterRepository productFilterRepository;

    @GetMapping
    public ResponseEntity<GlobalResponse> getShop(ProductSearchCond cond, Pageable pageable){
        Page<ProductPreviewDto> productPreview = productSearchRepository.search(cond, pageable);
        ShopResponseDto response = toShopResponseEntity(productPreview);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse> getShopByFilter(ProductFilterCond cond, Pageable pageable){
        Page<ProductPreviewDto> productPreview = productFilterRepository.get(cond, pageable);
        ShopResponseDto response = toShopResponseEntity(productPreview);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response), HttpStatus.OK);
    }

    private ShopResponseDto toShopResponseEntity(Page<ProductPreviewDto> response){
        ShopResponseDto.Pagination pagination = new ShopResponseDto.Pagination(response.isLast(), response.getTotalElements(), response.getTotalPages());
        return new ShopResponseDto(response.getContent(), pagination);
    }
}
