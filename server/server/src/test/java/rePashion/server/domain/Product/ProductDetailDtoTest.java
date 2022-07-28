package rePashion.server.domain.Product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.dto.ProductDetailDto;

import java.util.ArrayList;

public class ProductDetailDtoTest {

    Product product;
    ProductDetailDto productDetail;

    @BeforeEach
    public void setProduct(){
        this.product = Product.builder()
                .id(1L)
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
                .build();

        ProductDetailDto.SellerInfo sellerInfo = getSellerInfo();
        ProductDetailDto.Basic basic = getBasic(product);
        ProductDetailDto.SellerNotice sellerNotice = getSellerNotice(product);
        ProductDetailDto.Measure measure = getMeasure(product);

        this.productDetail = ProductDetailDto.builder()
                .sellerInfo(sellerInfo)
                .basic(basic)
                .sellerNotice(sellerNotice)
                .measure(measure)
                .opinion(product.getOpinion())
                .price(product.getPrice())
                .isIncludeDelivery(product.isIncludeDelivery())
                .updatedAt(product.getUpdatedAt())
                .like(getLikes())
                .view(product.getView())
                .build();
    }


    @Test
    public void when_isMe_should_true(){
        //given
        Long currentAccessedUser = 1000L;
        Long productHost = 1000L;

        //when
        Boolean isMe = productDetail.setIsMe(currentAccessedUser, productHost);

        //then
        Assertions.assertThat(productDetail.getIsMe()).isEqualTo(true);
        Assertions.assertThat(isMe).isEqualTo(true);
    }

    @Test
    public void when_isMe_should_false(){
        //given
        Long currentAccessedUser = 1000L;
        Long productHost = 1001L;

        //when
        Boolean isMe = productDetail.setIsMe(currentAccessedUser, productHost);

        //then
        Assertions.assertThat(productDetail.getIsMe()).isEqualTo(false);
        Assertions.assertThat(isMe).isEqualTo(false);
    }

    @Test
    public void when_getting_standard_image(){
        //given
        String exitstedImage = "";
        
        //when
        String profileImage = ProductDetailDto.determineProfileImage(exitstedImage);

        //then
        Assertions.assertThat(profileImage).isEqualTo(ProductDetailDto.STANDARD_PROFILE_IMAGE);
    }


    @Test
    public void when_getting_existing_image(){
        //given
        String existedImage = "https://webserver0712.s3.ap-northeast-2.amazonaws.com/profile/%EC%9B%B9%EC%97%91%EC%8A%A4_%EC%82%AC%EC%A7%84.png";

        //when
        String profileImage = ProductDetailDto.determineProfileImage(existedImage);

        //then
        Assertions.assertThat(profileImage).isEqualTo(existedImage);
    }

    @Test
    public void when_product_gender_is_male(){
        //given

        String
        //when

        //then
    }

    @Test
    public void when_product_gender_is_female(){

    }

    @Test
    public void when_product_gender_is_free(){

    }

    @Test
    public void when_product_measure_is_top(){

    }

    @Test
    public void when_product_measure_is_bottom(){

    }

    @Test
    public void when_product_measure_is_skirt(){

    }

    @Test
    public void when_product_measure_is_top_exception(){

    }

    @Test
    public void when_product_measure_is_bottom_exception(){

    }

    @Test
    public void when_product_measure_is_skirt_exception(){


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
        String profileImage = ProductDetailDto.determineProfileImage("");
        images.add("https://webserver0712.s3.ap-northeast-2.amazonaws.com/product/KakaoTalk_20220726_150206046_01.jpg");
        images.add("https://webserver0712.s3.ap-northeast-2.amazonaws.com/product/KakaoTalk_20220726_150206046.jpg");
        return new ProductDetailDto.SellerInfo(profileImage, "test01", images);
    }
}
