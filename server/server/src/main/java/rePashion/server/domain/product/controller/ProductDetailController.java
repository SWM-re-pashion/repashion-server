package rePashion.server.domain.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.product.exception.ProductNotExistedException;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.repository.ProductRepository;
import rePashion.server.domain.product.service.ProductDetailService;
import rePashion.server.global.common.auth.GetHeader;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductDetailController {

    private final GetHeader getHeader;
    private final ProductDetailService productDetailService;
    private final ProductRepository productRepository;

    @GetMapping("/detail/{id}")
    public ResponseEntity<GlobalResponse> getDetails(@PathVariable Long id, @RequestHeader Map<String, String> headers) throws JsonProcessingException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotExistedException(ErrorCode.PRODUCT_NOT_EXISTED));
        ProductDetailDto dto = productDetailService.of(id);
        productDetailService.setIsMe(dto, product.getId(), getHeader.of(headers, "authorization"));
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, dto), HttpStatus.OK);
    }
}
