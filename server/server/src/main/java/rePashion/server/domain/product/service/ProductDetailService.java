package rePashion.server.domain.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.user.exception.UserNotExistedException;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.domain.user.service.GetUserInfoService;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final GetUserInfoService getUserInfoService;
    private final UserRepository userRepository;

    public ProductDetailDto of(Long id){
        Product product = getProduct();
        return ProductDetailDto.builder()
                .sellerInfo(getSellerInfo())
                .basic(getBasic(product))
                .sellerNotice(getSellerNotice(product))
                .measure(getMeasure(product))
                .opinion(product.getOpinion())
                .price(product.getPrice())
                .isIncludeDelivery(product.isIncludeDelivery())
                .updatedAt(product.getUpdatedAt())
                .like(getLikes())
                .view(product.getView())
                .build();
    }

    public boolean setIsMe(ProductDetailDto productDetail, Long sellerId, String token) throws JsonProcessingException {
        boolean status = determineIsMe(sellerId, token);
        productDetail.setIsMe(status);
        return status;
    }

    private boolean determineIsMe(Long sellerId, String token) throws JsonProcessingException {
        Long currentUserPk = getUserInfoService.getPkOfUserInfo(token);
        Long findSellerId = userRepository.findUserById(sellerId).orElseThrow(() -> new UserNotExistedException(ErrorCode.USER_NOT_EXISTED)).getId();
        return currentUserPk.equals(findSellerId);
    }

    private int getLikes() {
        return 16;
    }

    private ProductDetailDto.Measure getMeasure(Product product) {
        return new ProductDetailDto.Measure(product.getTotalLength(), product.getShoulderWidth(), product.getChestSection(), product.getSleeveLength());
    }

    private ProductDetailDto.Basic getBasic(Product product) {
        String productInfo = product.getGender() + "용/" + product.getSize();
        String styleInfo = product.getMaterial() + "/" + product.getColor() + "/" + product.getTag();
        return new ProductDetailDto.Basic(product.getTitle(), product.getCategory(), product.getBrand(), productInfo, styleInfo);
    }

    private ProductDetailDto.SellerNotice getSellerNotice(Product product) {
        return new ProductDetailDto.SellerNotice(product.getCondition(),
                product.getPollution(),
                String.valueOf(product.getHeight()),
                product.getLength(),
                product.getBodyShape(),
                product.getFit(),
                product.getPurchaseTime(),
                product.getPurchasePlace());
    }

    private ProductDetailDto.SellerInfo getSellerInfo() {
        ArrayList<String> images = new ArrayList<>();
        String profileImage = "https://webserver0712.s3.ap-northeast-2.amazonaws.com/profile/%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84.png";
        images.add("https://webserver0712.s3.ap-northeast-2.amazonaws.com/product/KakaoTalk_20220726_150206046_01.jpg");
        images.add("https://webserver0712.s3.ap-northeast-2.amazonaws.com/product/KakaoTalk_20220726_150206046.jpg");
        return new ProductDetailDto.SellerInfo(profileImage, "test01", images);
    }

    private Product getProduct() {
        return Product.MockProduct();
    }
}
