package rePashion.server.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rePashion.server.domain.product.resources.request.Condition;
import rePashion.server.domain.product.dto.ProductPreviewDto;
import rePashion.server.domain.product.dto.ProductSearchCond;
import rePashion.server.domain.product.repository.ProductFilterRepository;
import rePashion.server.domain.product.repository.ProductSearchRepository;
import rePashion.server.domain.product.resources.response.Dto;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ProductSearchRepository productSearchRepository;
    private final ProductFilterRepository productFilterRepository;

    @GetMapping
    public ResponseEntity<GlobalResponse> getShop(Condition.SearchCond cond, Pageable pageable){
        Page<ProductPreviewDto> productPreview = productSearchRepository.search(cond, pageable);
        Dto.Shop response = toShopResponseEntity(productPreview);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<GlobalResponse> getShopByFilter(Condition.Filter cond, Pageable pageable){
        Page<ProductPreviewDto> productPreview = productFilterRepository.get(cond, pageable);
        Dto.Shop response = toShopResponseEntity(productPreview);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response), HttpStatus.OK);
    }

    public static Dto.Shop toShopResponseEntity(Page<ProductPreviewDto> response){
        Dto.Shop.Pagination pagination = new Dto.Shop.Pagination(response.isLast(), response.getTotalElements(), response.getTotalPages());
        return new Dto.Shop(response.getContent(), pagination);
    }
}
