package rePashion.server.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.product.dto.ProductPreviewDto;
import rePashion.server.domain.product.dto.ProductRecommendBody;
import rePashion.server.domain.product.dto.ProductRecommendDto;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final ProductRecommendCustomRepository productRecommendCustomRepository;
    private final ProductRecommendRepository productRecommendRepository;
    private final ProductRepository productRepository;

    private LocalDate lastEntered = LocalDate.now();
    private ProductRecommendDto todayRecommend;

    @GetMapping
    public ResponseEntity<GlobalResponse> getShop(Condition.Recommend cond, Pageable pageable){
        Page<ProductRecommend> productRecommends = productRecommendCustomRepository.get(cond, pageable);
        Dto.Shop response = toDto(productRecommends);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GlobalResponse> getAssociationsByProductId(@PathVariable Long productId){
        List<ProductRecommend> productRecommends = productRecommendCustomRepository.getAssociationsByProductId(productId);
        List<ProductPreviewDto> response = productRecommends.stream().map(productRecommend -> ProductPreviewDto.fromEntity(productRecommend.getAssociation())).collect(Collectors.toList());
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response), HttpStatus.OK);
    }

    @GetMapping("/today")
    public ResponseEntity<GlobalResponse> getTodayRecommend(){
        long totalCount = productRecommendRepository.count();
        if(lastEntered.isBefore(LocalDate.now()) || todayRecommend == null){
            int random = new Random().nextInt((int) totalCount);
            ProductRecommend productRecommend = productRecommendRepository.findById((long) random).get();
            todayRecommend = new ProductRecommendDto(productRecommend);
        }
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, todayRecommend), HttpStatus.OK);
    }

    private Dto.Shop toDto(Page<ProductRecommend> productRecommends) {
        Dto.Shop.Pagination pagination = new Dto.Shop.Pagination(productRecommends.isLast(), productRecommends.getTotalElements(), productRecommends.getTotalPages());
        List<ProductRecommendDto> collect = productRecommends.getContent().stream().map(ProductRecommendDto::new).collect(Collectors.toList());
        return new Dto.Shop(collect, pagination);
    }

    @PostMapping
    public String create(@RequestBody ProductRecommendBody body){
        Product product = productRepository.findById(body.getProductId()).orElseThrow(ProductNotExistedException::new);
        Product association = productRepository.findById(body.getAssociationId()).orElseThrow(ProductNotExistedException::new);
        ProductRecommend productRecommend = new ProductRecommend(product, association);
        productRecommendRepository.save(productRecommend);
        return "201";
    }
}

