package rePashion.server.domain.product.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import rePashion.server.domain.product.dto.ProductFilterCond;
import rePashion.server.domain.product.dto.ProductPreviewDto;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.ProductAdvanceInfo;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.product.model.embedded.SellerNote;
import rePashion.server.domain.statics.model.filter.Order;
import rePashion.server.global.common.config.JpaQueryFactoryConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ProductFilterRepository.class, JpaQueryFactoryConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductFilterRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAdvanceInfoRepository productAdvanceInfoRepository;

    @Autowired
    private ProductFilterRepository productFilterRepository;

    @BeforeEach
    public void initDB(){
        ArrayList<BasicInfo> basicInfos = new ArrayList<>();
        ArrayList<SellerNote> sellerNotes = new ArrayList<>();

        basicInfos.add(BasicInfo.builder()
                .title("title01")
                .contact("test01@test.com")
                .category("1001001")
                .brand("brand01")
                .thumbnailImage("https://image01")
                .price(200000)
                .isIncludeDelivery(true)
                .size("XS")
                .build());

        basicInfos.add(BasicInfo.builder()
                .title("title02")
                .contact("test02@test.com")
                .category("1001001")
                .brand("brand02")
                .thumbnailImage("https://image02")
                .price(200001)
                .isIncludeDelivery(true)
                .size("XS")
                .build());

        basicInfos.add(BasicInfo.builder()
                .title("title03")
                .contact("test03@test.com")
                .category("1001001")
                .brand("brand03")
                .thumbnailImage("https://image03")
                .price(200002)
                .isIncludeDelivery(true)
                .size("XS")
                .build());

        basicInfos.add(BasicInfo.builder()
                .title("title04")
                .contact("test04@test.com")
                .category("1001001")
                .brand("brand04")
                .thumbnailImage("https://image04")
                .price(200003)
                .isIncludeDelivery(true)
                .size("XS")
                .build());

        basicInfos.add(BasicInfo.builder()
                .title("title05")
                .contact("test05@test.com")
                .category("1001001")
                .brand("brand05")
                .thumbnailImage("https://image05")
                .price(200004)
                .isIncludeDelivery(true)
                .size("S")
                .build());

        sellerNotes.add(
                SellerNote
                        .builder()
                        .length("crop")
                        .fit("tight")
                        .tag("hiphop")
                        .color("BLACK")
                        .build());

        sellerNotes.add(
                SellerNote
                        .builder()
                        .length("crop")
                        .fit("normal")
                        .tag("kitsch")
                        .color("GREEN")
                        .build());

        sellerNotes.add(
                SellerNote
                        .builder()
                        .length("waist")
                        .fit("tight")
                        .tag("kitsch")
                        .color("MINT")
                        .build());

        sellerNotes.add(
                SellerNote
                        .builder()
                        .length("waist")
                        .fit("normal")
                        .tag("kitsch")
                        .color("PURPLE")
                        .build());

        sellerNotes.add(
                SellerNote
                        .builder()
                        .length("waist")
                        .fit("oversize")
                        .tag("street")
                        .color("PINK")
                        .build());

        for(int i =0; i < 5; i++){
            Product p = Product.builder().basicInfo(basicInfos.get(i)).build();
            ProductAdvanceInfo pai = ProductAdvanceInfo.builder().sellerNote(sellerNotes.get(i)).build();
            pai.changeProduct(p);
            productAdvanceInfoRepository.save(pai);
            productRepository.save(p);
        }
    }

    @Test
    public void kitsch_스타일_조회(){
        //given
        List<String> kitsch = List.of("kitsch");
        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setStyle(kitsch);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(3);
        assertThat(content.get(0)).extracting("title").isEqualTo("title04");
        assertThat(content.get(1)).extracting("title").isEqualTo("title03");
        assertThat(content.get(2)).extracting("title").isEqualTo("title02");
    }

    @Test
    public void kitsch_and_hiphop_스타일_조회(){
        //given
        List<String> kitsch = List.of("kitsch", "street");
        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setStyle(kitsch);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(4);
        assertThat(content.get(0)).extracting("title").isEqualTo("title05");
        assertThat(content.get(1)).extracting("title").isEqualTo("title04");
        assertThat(content.get(2)).extracting("title").isEqualTo("title03");
        assertThat(content.get(3)).extracting("title").isEqualTo("title02");
    }

    @Test
    public void BLACK_색깔_조회(){
        //given
        List<String> colors = List.of("BLACK");
        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setColor(colors);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0)).extracting("title").isEqualTo("title01");
    }

    @Test
    public void BLACK_and_PINK_색깔_조회(){
        //given
        List<String> colors = List.of("BLACK", "PINK");
        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setColor(colors);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0)).extracting("title").isEqualTo("title05");
        assertThat(content.get(1)).extracting("title").isEqualTo("title01");
    }

    @Test
    public void normal_fit_조회(){
        //given
        List<String> fits = List.of("normal");
        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setFit(fits);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0)).extracting("title").isEqualTo("title04");
        assertThat(content.get(1)).extracting("title").isEqualTo("title02");
    }

    @Test
    public void normal_and_tight_fit_조회(){
        //given
        List<String> fits = List.of("normal", "tight");
        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setFit(fits);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(4);
        assertThat(content.get(0)).extracting("title").isEqualTo("title04");
        assertThat(content.get(1)).extracting("title").isEqualTo("title03");
        assertThat(content.get(2)).extracting("title").isEqualTo("title02");
        assertThat(content.get(3)).extracting("title").isEqualTo("title01");
    }

    @Test
    public void crop_length_조회(){
        //given
        List<String> lengths  = List.of("crop");
        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setLength(lengths);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0)).extracting("title").isEqualTo("title02");
        assertThat(content.get(1)).extracting("title").isEqualTo("title01");
    }

    @Test
    public void crop_and_waist_length_조회(){
        //given
        List<String> fits = List.of("crop", "waist");
        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setLength(fits);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(5);
        assertThat(content.get(0)).extracting("title").isEqualTo("title05");
        assertThat(content.get(1)).extracting("title").isEqualTo("title04");
        assertThat(content.get(2)).extracting("title").isEqualTo("title03");
        assertThat(content.get(3)).extracting("title").isEqualTo("title02");
        assertThat(content.get(4)).extracting("title").isEqualTo("title01");
    }

    @Test
    public void XS_size_조회(){
        //given
        List<String> sizes  = List.of("XS");
        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setClothesSize(sizes);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(4);
        assertThat(content.get(0)).extracting("title").isEqualTo("title04");
        assertThat(content.get(1)).extracting("title").isEqualTo("title03");
        assertThat(content.get(2)).extracting("title").isEqualTo("title02");
        assertThat(content.get(3)).extracting("title").isEqualTo("title01");
    }

    @Test
    public void XS_and_S_length_조회(){
        //given
        List<String> sizes = List.of("XS", "S");
        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setClothesSize(sizes);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(5);
        assertThat(content.get(0)).extracting("title").isEqualTo("title05");
        assertThat(content.get(1)).extracting("title").isEqualTo("title04");
        assertThat(content.get(2)).extracting("title").isEqualTo("title03");
        assertThat(content.get(3)).extracting("title").isEqualTo("title02");
        assertThat(content.get(4)).extracting("title").isEqualTo("title01");
    }

    @Test
    public void kitsch_Style_or_BLACK_and_MINT_Color_조회(){
        //given
        List<String> styles = List.of("kitsch");
        List<String> colors = List.of("GREEN", "PURPLE");

        ProductFilterCond cond = new ProductFilterCond();
        cond.setCategory("1001001");
        cond.setStyle(styles);
        cond.setColor(colors);
        cond.setOrder(Order.latest);

        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productFilterRepository.get(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0)).extracting("title").isEqualTo("title04");
        assertThat(content.get(1)).extracting("title").isEqualTo("title02");
    }
}