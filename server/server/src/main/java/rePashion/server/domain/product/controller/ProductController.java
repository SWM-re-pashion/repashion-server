package rePashion.server.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.auth.dto.exception.UserNotExistedException;
import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.product.dto.ProductDto;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.service.ProductService;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<GlobalResponse> create(@AuthenticationPrincipal Long userId, @RequestBody ProductDto dto){
        User user = findUser(userId);
        Product savedProduct = productService.save(user, dto);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.CREATED, savedProduct.getId()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse> update(@AuthenticationPrincipal Long userId, @PathVariable Long id, @RequestBody ProductDto dto){
        User user = findUser(userId);
        Long updatedId = productService.update(user, id, dto);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, updatedId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse> get(@AuthenticationPrincipal Long userId, @PathVariable Long id){
        User user = findUser(userId);
        ProductDto productDto = productService.get(user, id);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, productDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse> delete(@AuthenticationPrincipal Long userId, @PathVariable Long id){
        User user = findUser(userId);
        productService.delete(user, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<GlobalResponse> getDetail(@AuthenticationPrincipal Long userId, @PathVariable Long productId){
        User user = findUser(userId);
        ProductDetailDto detail = productService.getDetail(user, productId);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, detail), HttpStatus.OK);
    }

    private User findUser(Long userId){
        return userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
    }
}
