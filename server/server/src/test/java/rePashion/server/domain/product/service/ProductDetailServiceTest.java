package rePashion.server.domain.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.weaver.patterns.IToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.domain.user.service.GetUserInfoService;
import rePashion.server.global.common.auth.GetHeader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductDetailServiceTest {

    @InjectMocks
    private ProductDetailService productDetailService;

    @Mock
    private GetUserInfoService getUserInfoService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void when_isMe_should_true() throws JsonProcessingException {

        //given
        Long userId = 10L;
        User user = getUser(userId);
        Product product = getProduct(user);
        ProductDetailDto.SellerInfo sellerInfo = getSellerInfo();
        ProductDetailDto.Basic basic = getBasic(product);
        ProductDetailDto.SellerNotice sellerNotice = getSellerNotice(product);
        ProductDetailDto.Measure measure = getMeasure(product);
        ProductDetailDto productDetail = getProductDetailDto(sellerInfo, basic, sellerNotice, measure, product);
        String token = "I*UI#Kfdklsfiouufi";

        //when
        when(getUserInfoService.getPkOfUserInfo(token)).thenReturn(userId);
        when(userRepository.findUserById(any())).thenReturn(Optional.of(user));
        boolean isMe = productDetailService.setIsMe(productDetail, product.getSeller().getId(), token);

        //then
        Assertions.assertThat(isMe).isEqualTo(true);
    }

    @Test
    public void when_isMe_should_false() throws JsonProcessingException {

        //given
        Long userId = 11L;
        User user = getUser(10L);
        Product product = getProduct(user);
        ProductDetailDto.SellerInfo sellerInfo = getSellerInfo();
        ProductDetailDto.Basic basic = getBasic(product);
        ProductDetailDto.SellerNotice sellerNotice = getSellerNotice(product);
        ProductDetailDto.Measure measure = getMeasure(product);
        ProductDetailDto productDetailDto = getProductDetailDto(sellerInfo, basic, sellerNotice, measure, product);
        String token = getToken();

        //when
        when(getUserInfoService.getPkOfUserInfo(token)).thenReturn(userId);
        when(userRepository.findUserById(any())).thenReturn(Optional.of(user));
        boolean isMe = productDetailService.setIsMe(productDetailDto, product.getSeller().getId(), token);

        //then
        Assertions.assertThat(isMe).isEqualTo(false);
    }

    private String getToken() {
        return "I*UI#Kfdklsfiouufi";
    }

    private ProductDetailDto getProductDetailDto(ProductDetailDto.SellerInfo sellerInfo, ProductDetailDto.Basic basic, ProductDetailDto.SellerNotice sellerNotice, ProductDetailDto.Measure measure, Product product) {
        ProductDetailDto productDetail = ProductDetailDto.builder()
                .sellerInfo(sellerInfo)
                .basic(basic)
                .sellerNotice(sellerNotice)
                .measure(measure)
                .opinion(product.getOpinion())
                .price(product.getPrice())
                .isIncludeDelivery(product.isIncludeDelivery())
                .updatedAt(LocalDateTime.now())
                .like(getLikes())
                .view(product.getView())
                .build();
        return productDetail;
    }

    private User getUser(Long userId) {
        User user = User.builder()
                .id(userId)
                .nickName("hihi")
                .email("test@test.com")
                .build();
        return user;
    }

    private Product getProduct(User user) {
        Product product = Product.builder()
                .title("스투시 프린팅 티셔츠")
                .category("상의")
                .brand("스투시")
                .price(2000)
                .isIncludeDelivery(true)
                .gender("남성")
                .size("XL")
                .purchaseTime("한달_전")
                .purchasePlace("스투시 온라인 매장")
                .condition("새 상품")
                .pollution("전혀_없음")
                .height(170)
                .bodyShape("뚱뚱")
                .length("크롭")
                .fit("타이트")
                .opinion("안 입는데 옷장에 있어서")
                .tag("힙합")
                .color("블랙")
                .material("퍼")
                .totalLength(73)
                .shoulderWidth(53)
                .waistSection(-1)
                .chestSection(59)
                .thighSection(-1)
                .bottomSection(-1)
                .rise(-1)
                .sleeveLength(100)
                .seller(user)
                .build();
        return product;
    }

    private String determineProfileImage(String exitstedImage) {
        return exitstedImage.equals("") ? ProductDetailDto.STANDARD_PROFILE_IMAGE : exitstedImage;
    }

    private int getLikes() {
        int likes = 17;
        return likes;
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
        String profileImage = determineProfileImage("");
        images.add("https://webserver0712.s3.ap-northeast-2.amazonaws.com/product/KakaoTalk_20220726_150206046_01.jpg");
        images.add("https://webserver0712.s3.ap-northeast-2.amazonaws.com/product/KakaoTalk_20220726_150206046.jpg");
        return new ProductDetailDto.SellerInfo(profileImage, "test01", images);
    }
}