package rePashion.server.domain.Product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rePashion.server.domain.product.model.Measure;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.dto.ProductDetailDto;
import rePashion.server.global.common.measure.MeasureConfig;
import rePashion.server.global.common.measure.MeasureRepository;
import rePashion.server.global.common.measure.MeasureType;
import rePashion.server.global.common.measure.exception.MeasureException;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.ArrayList;
import java.util.HashMap;

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
                .isMe(determineIsMe(1000L, 1000L))
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
        Boolean isMe = determineIsMe(currentAccessedUser, productHost);

        //then
        Assertions.assertThat(isMe).isEqualTo(true);
    }

    @Test
    public void when_isMe_should_false(){
        //given
        Long currentAccessedUser = 1000L;
        Long productHost = 1001L;

        //when
        Boolean isMe = determineIsMe(currentAccessedUser, productHost);

        //then
        Assertions.assertThat(isMe).isEqualTo(false);
    }

    @Test
    public void when_getting_standard_image(){
        //given
        String exitstedImage = "";
        
        //when
        String profileImage = determineProfileImage(exitstedImage);

        //then
        Assertions.assertThat(profileImage).isEqualTo(ProductDetailDto.STANDARD_PROFILE_IMAGE);
    }


    @Test
    public void when_getting_existing_image(){
        //given
        String existedImage = "https://webserver0712.s3.ap-northeast-2.amazonaws.com/profile/%EC%9B%B9%EC%97%91%EC%8A%A4_%EC%82%AC%EC%A7%84.png";

        //when
        String profileImage = determineProfileImage(existedImage);

        //then
        Assertions.assertThat(profileImage).isEqualTo(existedImage);
    }

    @Test
    public void when_product_measure_is_top(){

        //given
        Measure measure = Measure.builder()
                .totalLength(100)
                .shoulderWidth(200)
                .chestSection(300)
                .sleeveLength(400)
                .build();

        //when
        MeasureRepository measureRepository = MeasureConfig.determinMeasure(MeasureType.TOP);
        HashMap<String, Integer> map = measureRepository.getMeasure(MeasureType.TOP, measure);

        //then
        Assertions.assertThat(map.get("totalLength")).isEqualTo(100);
        Assertions.assertThat(map.get("shoulderWidth")).isEqualTo(200);
        Assertions.assertThat(map.get("chestSection")).isEqualTo(300);
        Assertions.assertThat(map.get("sleeveLength")).isEqualTo(400);
    }

    @Test
    public void when_product_measure_is_bottom(){
        //given
        Measure measure = Measure.builder()
                .totalLength(100)
                .waistSection(200)
                .thighSection(300)
                .rise(400)
                .bottomSection(500)
                .build();

        //when
        MeasureRepository measureRepository = MeasureConfig.determinMeasure(MeasureType.BOTTOM);
        HashMap<String, Integer> map = measureRepository.getMeasure(MeasureType.BOTTOM, measure);

        //then
        Assertions.assertThat(map.get("totalLength")).isEqualTo(100);
        Assertions.assertThat(map.get("waistSection")).isEqualTo(200);
        Assertions.assertThat(map.get("thighSection")).isEqualTo(300);
        Assertions.assertThat(map.get("rise")).isEqualTo(400);
        Assertions.assertThat(map.get("bottomSection")).isEqualTo(500);
    }

    @Test
    public void when_product_measure_is_skirt(){
        //given
        Measure measure = Measure.builder()
                .totalLength(100)
                .waistSection(200)
                .bottomSection(300)
                .build();

        //when
        MeasureRepository measureRepository = MeasureConfig.determinMeasure(MeasureType.SKIRT);
        HashMap<String, Integer> map = measureRepository.getMeasure(MeasureType.SKIRT, measure);

        //then
        Assertions.assertThat(map.get("totalLength")).isEqualTo(100);
        Assertions.assertThat(map.get("waistSection")).isEqualTo(200);
        Assertions.assertThat(map.get("bottomSection")).isEqualTo(300);
    }

    @Test
    public void when_product_measure_is_top_exception_have_incorrect_pramateter(){
        Measure measure = Measure.builder()
                .totalLength(100)
                .waistSection(200)
                .thighSection(300)
                .rise(400)
                .build();

        //when
        Assertions.assertThatThrownBy(() -> {
                    MeasureRepository measureRepository = MeasureConfig.determinMeasure(MeasureType.TOP);
                    measureRepository.getMeasure(MeasureType.TOP, measure);
                })
                .isInstanceOf(MeasureException.class)
                .hasMessage(ErrorCode.MEASURE_DATA_ERROR.getMessage());
    }

    @Test
    public void when_product_measure_is_top_exception_have_less_pramateter(){
        Measure measure = Measure.builder()
                .totalLength(100)
                .build();

        //when
        Assertions.assertThatThrownBy(() -> {
                    MeasureRepository measureRepository = MeasureConfig.determinMeasure(MeasureType.TOP);
                    measureRepository.getMeasure(MeasureType.TOP, measure);
                })
                .isInstanceOf(MeasureException.class)
                .hasMessage(ErrorCode.MEASURE_DATA_ERROR.getMessage());
    }

    @Test
    public void when_product_measure_is_bottom_exception(){

    }

    @Test
    public void when_product_measure_is_skirt_exception(){

    }

    @Test
    public void likes_added_when_it_is_first_attempt(){ //Product에 옮기기

    }

    @Test
    public void likes_not_added_but_exception_when_it_is_not_first_attempt(){   //Product에 옮기기

    }

    @Test
    public void view_added_when_it_is_first_attempt(){   //Product에 옮기기

    }

    @Test
    public void view_not_added_but_exception_when_it_is_not_first_attempt(){   //Product에 옮기기

    }

    private String determineProfileImage(String exitstedImage) {
        return exitstedImage.equals("") ? ProductDetailDto.STANDARD_PROFILE_IMAGE : exitstedImage;
    }


    private Boolean determineIsMe(Long currentAccessedUser, Long productHost){
        return (currentAccessedUser.equals(productHost));
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
