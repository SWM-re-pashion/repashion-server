package rePashion.server.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.product.service.ProductDetailService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/product/detail")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse> getDetails(@PathVariable Long id){
        ProductDetailDto dto = productDetailService.get(id);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, dto), HttpStatus.OK);
    }
}
