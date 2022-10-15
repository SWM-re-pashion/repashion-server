package rePashion.server.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.product.dto.ProductFlatDto;
import rePashion.server.domain.product.dto.ProductRequestBody;
import rePashion.server.domain.product.exception.ProductBuyerException;
import rePashion.server.domain.product.exception.ProductNotExistedException;
import rePashion.server.domain.product.model.*;
import rePashion.server.domain.product.model.measure.MeasureMapper;
import rePashion.server.domain.product.model.measure.entity.Measure;
import rePashion.server.domain.product.repository.MeasureRepository;
import rePashion.server.domain.product.repository.ProductAdvanceInfoRepository;
import rePashion.server.domain.product.repository.ProductImageRepository;
import rePashion.server.domain.product.repository.ProductRepository;
import rePashion.server.domain.product.resources.mapper.ProductMapper;
import rePashion.server.domain.user.model.PurchaseStatus;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.model.UserProduct;
import rePashion.server.domain.user.repository.UserProductRepository;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductAdvanceInfoRepository productAdvanceInfoRepository;
    private final ProductImageRepository productImageRepository;
    
    private final UserProductRepository userProductRepository;
    private final MeasureRepository measureRepository;
    private final MeasureMapper measureMapper;
    private final ProductMapper productMapper;
    public Product save(User user, ProductRequestBody dto){
        ProductFlatDto productFlatDto = productMapper.productDtoToFlatDto(dto);
        Product savedProduct = saveProduct(productFlatDto, user, dto.getImgList().get(0));
        ProductAdvanceInfo savedProductAdvanceInfo = saveProductAdvanceInfo(productFlatDto, savedProduct);
        saveMeasure(dto, savedProductAdvanceInfo);
        saveProductImage(dto.getImgList(), savedProduct);
        return savedProduct;
    }

    private void saveProductImage(ArrayList<String> imgList, Product product){
        imgList.forEach((o) -> {
            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .imagePath(o)
                    .build();
            productImageRepository.save(productImage);
        });
    }

    private ProductAdvanceInfo saveProductAdvanceInfo(ProductFlatDto productFlatDto, Product savedProduct) {
        ProductAdvanceInfo productAdvanceInfo = productMapper.flatDtoToProductAdvanceIfo(productFlatDto);
        productAdvanceInfo.changeProduct(savedProduct);
        return productAdvanceInfoRepository.save(productAdvanceInfo);
    }

    private void saveMeasure(ProductRequestBody body, ProductAdvanceInfo advanceInfo){
        Measure measure = measureMapper.getMeasure(body);
        measure.setAdvanceInfo(advanceInfo);
        measureRepository.save(measure);
    }

    private Product saveProduct(ProductFlatDto dto, User user, String image) {
        Product product = productMapper.flatDtoToProduct(dto);
        product.getBasicInfo().changeThumbNail(image);
        Product savedProduct = productRepository.save(product);
        UserProduct userProduct = new UserProduct(PurchaseStatus.Buyer);
        userProduct.changeUserAndProduct(user, savedProduct);
        userProductRepository.save(userProduct);
        return savedProduct;
    }

    public Long update(User user, Long productId, ProductRequestBody dto){
        delete(user, productId);
        Product savedProduct = save(user, dto);
        return savedProduct.getId();
    }

    public void delete(User user, Long productId){
        checkUser(user, productId);
        productRepository.deleteById(productId);
    }

//    public ProductRequestBody get(User user, Long productId){
//        checkUser(user, productId);
//        Product product = productRepository.findProductEntityGraph(productId).orElseThrow(ProductNotExistedException::new);
//        return ProductRequestBody.toDto(product);
//    }

    private void checkUser(User user, Long productId){
        Product findProduct = productRepository.findById(productId).orElseThrow(ProductNotExistedException::new);
        User productBuyer = userProductRepository.findProductBuyer(findProduct).orElseThrow(()->new ProductBuyerException(ErrorCode.PRODUCT_BUYER_NOT_EXISTED));
        if(!productBuyer.getId().equals(user.getId())) throw new ProductBuyerException(ErrorCode.PRODUCT_BUYER_NOT_MATCH);
    }
}
