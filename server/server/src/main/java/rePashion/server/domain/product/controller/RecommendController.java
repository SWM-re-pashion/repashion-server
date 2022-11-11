package rePashion.server.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rePashion.server.domain.product.dto.ProductRecommendBody;
import rePashion.server.domain.product.exception.ProductNotExistedException;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.ProductRecommend;
import rePashion.server.domain.product.repository.ProductRecommendCustomRepository;
import rePashion.server.domain.product.repository.ProductRecommendRepository;
import rePashion.server.domain.product.repository.ProductRepository;
import rePashion.server.domain.product.resources.request.Condition;
import rePashion.server.domain.product.resources.response.Dto;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

import java.util.Optional;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final ProductRecommendCustomRepository productRecommendCustomRepository;
    private final ProductRecommendRepository productRecommendRepository;
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<GlobalResponse> getShop(Condition.Recommend cond, Pageable pageable){
        Page<ProductRecommend> productRecommends = productRecommendCustomRepository.get(cond, pageable);
        Dto.Shop.Pagination pagination = new Dto.Shop.Pagination(productRecommends.isLast(), productRecommends.getTotalElements(), productRecommends.getTotalPages());
        Dto.Shop response = new Dto.Shop(productRecommends.getContent(), pagination);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response), HttpStatus.OK);
    }

    @PostMapping
    public String create(ProductRecommendBody body){
        Product product = productRepository.findById(body.getProductId()).orElseThrow(ProductNotExistedException::new);
        Product association = productRepository.findById(body.getAssociationId()).orElseThrow(ProductNotExistedException::new);
        ProductRecommend productRecommend = new ProductRecommend(product, association);
        productRecommendRepository.save(productRecommend);
        return "201";
    }
}

