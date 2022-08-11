package rePashion.server.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.product.dto.ProductCreateDto;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.service.ProductService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<GlobalResponse> create(@RequestBody ProductCreateDto dto){
        Product savedProduct = productService.save(dto);
        return new ResponseEntity<GlobalResponse>(GlobalResponse.of(StatusCode.CREATED, savedProduct.getId()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse> update(@PathVariable Long id, @RequestBody ProductCreateDto dto){
        Long updatedId = productService.update(id, dto);
        return new ResponseEntity<GlobalResponse>(GlobalResponse.of(StatusCode.SUCCESS, updatedId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse> get(@PathVariable Long id){
        ProductCreateDto dto = productService.get(id);
        return new ResponseEntity<GlobalResponse>(GlobalResponse.of(StatusCode.SUCCESS, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse> delete(@PathVariable Long id){
        productService.delete(id);
        return new ResponseEntity<GlobalResponse>(HttpStatus.NO_CONTENT);
    }
}
