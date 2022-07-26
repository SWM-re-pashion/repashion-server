package rePashion.server.domain.Product;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.ProductAdvanceInfo;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProductTest {

    Product product;

    @BeforeEach
    public void setUp(){
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
                .reason("안 입는데 옷장에 있어서")
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
    }

    @Test
    public void get_advance_info_when_product_page_build(){
        ArrayList<String> category = new ArrayList<>();
        category.add("상의");
        category.add("하의");
        category.add("원피스");
        category.add("스커트");

        ArrayList<String> gender = new ArrayList<>();
        gender.add("남성");
        gender.add("여성");
        gender.add("남녀공용");

        ArrayList<String> top = new ArrayList<>();
        top.add("XL");
        top.add("2XL");
        top.add("3XL");
        ArrayList<String> bottom = new ArrayList<>();
        bottom.add("23");
        bottom.add("24");
        bottom.add("25");
        ProductAdvanceInfo.Size size = new ProductAdvanceInfo.Size(top, bottom);

        ArrayList<String> condition = new ArrayList<>();
        condition.add("새_상품");
        condition.add("거의_없음");
        condition.add("보통");
        condition.add("조금_있음");
        condition.add("많이_있음");

        ArrayList<String> pollution = new ArrayList<>();
        pollution.add("전혀_없음");
        pollution.add("외관상_안 보임");
        pollution.add("자세히_보면_티남");
        pollution.add("잘_보임");

        ArrayList<String> bodyShape = new ArrayList<>();
        bodyShape.add("마름");
        bodyShape.add("보통");
        bodyShape.add("통통");
        bodyShape.add("뚱뚱");

        ArrayList<String> length = new ArrayList<>();
        length.add("크롭");
        length.add("허리");
        length.add("골반");
        length.add("엉덩이");
        length.add("허벅지");
        length.add("무릎");
        length.add("정강이");
        length.add("발목");
        length.add("발");

        ArrayList<String> fitTop = new ArrayList<>();
        fitTop.add("타이트");
        fitTop.add("노멀");
        fitTop.add("오버사이즈");
        ArrayList<String> fitBottom = new ArrayList<>();
        fitBottom.add("스키니");
        fitBottom.add("노멀");
        fitBottom.add("와이드");
        fitBottom.add("루즈");
        ProductAdvanceInfo.Fit fit = new ProductAdvanceInfo.Fit(fitTop, fitBottom);

        ArrayList<String> tag = new ArrayList<>();
        tag.add("합합");
        tag.add("펑크");
        tag.add("모던");
        tag.add("스트리트");
        tag.add("키치/키덜트");
        tag.add("스포티");
        tag.add("클래식");
        tag.add("레트로");
        tag.add("아방가르드");
        tag.add("섹시");
        tag.add("톰보이");
        tag.add("프레피");

        ArrayList<ProductAdvanceInfo.Colors> colors = new ArrayList<>();
        colors.add(new ProductAdvanceInfo.Colors("Black", "#000"));

        ArrayList<String> materials = new ArrayList<>();
        materials.add("퍼");
        materials.add("무스탕");
        materials.add("스웨이드");

        ProductAdvanceInfo info = ProductAdvanceInfo.builder()
                .category(category)
                .gender(gender)
                .size(size)
                .condition(condition)
                .pollution(pollution)
                .bodyShape(bodyShape)
                .length(length)
                .fit(fit)
                .tag(tag)
                .colors(colors)
                .materials(materials)
                .build();

        int insertedHeight = 170;
        String insertedBodyShape = "뚱뚱" ;
        info.enterHeightAndBodyShapeInfo(insertedHeight, insertedBodyShape);
    }

    @Test
    public void refreshing_product_cycle(){
        LocalDate now = LocalDate.now();
        product.updateTime();
        LocalDate updatedAt = product.getUpdatedAt();
        Assertions.assertThat(now).isEqualTo(updatedAt);
        System.out.println(updatedAt);
    }
}
