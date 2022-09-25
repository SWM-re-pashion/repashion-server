package rePashion.server.domain.product.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rePashion.server.domain.product.dto.ProductPreviewDto;
import rePashion.server.domain.product.dto.ProductSearchCond;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.statics.model.filter.Order;
import rePashion.server.global.common.config.JpaQueryFactoryConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ProductSearchRepository.class, JpaQueryFactoryConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductSearchRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSearchRepository productSearchRepository;

    //5개 Product mock 데이터 넣어주기
    @BeforeEach
    public void initDB(){
        BasicInfo basicInfo1 = BasicInfo.builder()
                .title("test-title-01")
                .contact("test01@test.com")
                .category("1002004")
                .brand("brand01")
                .thumbnailImage("https://image01")
                .price(200001)
                .isIncludeDelivery(true)
                .size("XS")
                .build();
        increaseLikes(basicInfo1, 3);
        increaseViews(basicInfo1, 3);
        Product product1 = Product.builder()
                .id(1L)
                .basicInfo(basicInfo1)
                .build();
        productRepository.save(product1);

        BasicInfo basicInfo2 = BasicInfo.builder()
                .title("test-title-02")
                .contact("test02@test.com")
                .category("1002004")
                .brand("brand02")
                .thumbnailImage("https://image02")
                .price(342834)
                .isIncludeDelivery(true)
                .size("XS")
                .build();
        increaseLikes(basicInfo2, 2);
        increaseViews(basicInfo2, 2);
        Product product2 = Product.builder()
                .id(2L)
                .basicInfo(basicInfo2)
                .build();
        productRepository.save(product2);

        BasicInfo basicInfo3 = BasicInfo.builder()
                .title("test-title-03")
                .contact("test03@test.com")
                .category("1002004")
                .brand("brand03")
                .thumbnailImage("https://image03")
                .price(48954)
                .isIncludeDelivery(true)
                .size("XS")
                .build();
        increaseLikes(basicInfo3, 1);
        increaseViews(basicInfo3, 5);
        Product product3 = Product.builder()
                .id(3L)
                .basicInfo(basicInfo3)
                .build();
        productRepository.save(product3);

        BasicInfo basicInfo4 = BasicInfo.builder()
                .title("test-title-04")
                .contact("test04@test.com")
                .category("1002004")
                .brand("brand04")
                .thumbnailImage("https://image04")
                .price(8954)
                .isIncludeDelivery(true)
                .size("XS")
                .build();
        increaseLikes(basicInfo4, 5);
        increaseViews(basicInfo4, 1);
        Product product4 = Product.builder()
                .id(4L)
                .basicInfo(basicInfo4)
                .build();
        productRepository.save(product4);

        BasicInfo basicInfo5 = BasicInfo.builder()
                .title("test-title-05")
                .contact("test05@test.com")
                .category("1002004")
                .brand("brand04")
                .thumbnailImage("https://image05")
                .price(328412)
                .isIncludeDelivery(true)
                .size("XS")
                .build();
        increaseLikes(basicInfo5, 8);
        increaseViews(basicInfo5, 4);
        Product product5 = Product.builder()
                .id(5L)
                .basicInfo(basicInfo5)
                .build();
        productRepository.save(product5);

        BasicInfo basicInfo6 = BasicInfo.builder()
                .title("test-title-06")
                .contact("test06@test.com")
                .category("1002005")
                .brand("brand04")
                .thumbnailImage("https://image06")
                .price(328412)
                .isIncludeDelivery(true)
                .size("XS")
                .build();
        increaseLikes(basicInfo6, 8);
        increaseViews(basicInfo6, 6);
        Product product6 = Product.builder()
                .id(6L)
                .basicInfo(basicInfo6)
                .build();
        productRepository.save(product6);

        BasicInfo basicInfo7 = BasicInfo.builder()
                .title("test-title-OnSale")
                .contact("test07@test.com")
                .category("1002001")
                .brand("brand")
                .thumbnailImage("https://image07")
                .price(10000)
                .isIncludeDelivery(true)
                .size("XS")
                .build();
        increaseLikes(basicInfo7, 8);
        increaseViews(basicInfo7, 6);
        Product product7 = Product.builder()
                .basicInfo(basicInfo7)
                .build();
        productRepository.save(product7);

        BasicInfo basicInfo8 = BasicInfo.builder()
                .title("test-title-SoldOut")
                .contact("test08@test.com")
                .category("1002001")
                .brand("brand")
                .thumbnailImage("https://image08")
                .price(100123)
                .isIncludeDelivery(true)
                .size("XS")
                .build();
        increaseLikes(basicInfo8, 2);
        increaseViews(basicInfo8, 3);
        Product product8 = Product.builder()
                .id(8L)
                .basicInfo(basicInfo8)
                .build();
        product8.getBasicInfo().changeStatus();
        productRepository.save(product8);
    }

    private void increaseLikes(BasicInfo basicInfo, int n){
        for(int i =0; i < n; i++)
            basicInfo.increaseLikes();
    }

    private void increaseViews(BasicInfo basicInfo, int n){
        for(int i =0; i < n; i++)
            basicInfo.increaseViews();
    }

    @Test
    public void 첫번째_페이지의_페이지네이션_확인(){
        //given
        ProductSearchCond cond = new ProductSearchCond("1002004", "high_price", false);
        PageRequest of = PageRequest.of(0, 3);

        //when
        Page<ProductPreviewDto> dto = productSearchRepository.search(cond, of);
        List<ProductPreviewDto> content = dto.getContent();
        Pageable pageable = dto.getPageable();

        //then
        assertThat(content.size()).isEqualTo(3);
        assertThat(pageable.getOffset()).isEqualTo(0);
        assertThat(pageable.getPageSize()).isEqualTo(3);
        assertThat(pageable.hasPrevious()).isEqualTo(false);
    }

    @Test
    public void 조회순_조회(){
        //given
        ProductSearchCond cond = new ProductSearchCond("1002004", "view", false);
        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productSearchRepository.search(cond, of);
        List<ProductPreviewDto> content = dto.getContent();
        Pageable pageable = dto.getPageable();

        //then
        assertThat(content.size()).isEqualTo(5);
        assertThat(content.get(0).getImg()).isEqualTo("https://image03");
        assertThat(content.get(4).getImg()).isEqualTo("https://image04");
    }

    @Test
    public void 인기순_조회(){
        //given
        ProductSearchCond cond = new ProductSearchCond("1002004", "like", false);
        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productSearchRepository.search(cond, of);
        List<ProductPreviewDto> content = dto.getContent();
        Pageable pageable = dto.getPageable();

        //then
        assertThat(content.size()).isEqualTo(5);
        assertThat(content.get(0).getImg()).isEqualTo("https://image05");
        assertThat(content.get(4).getImg()).isEqualTo("https://image03");
    }

    @Test
    public void 높은가격순_조회(){
        //given
        ProductSearchCond cond = new ProductSearchCond("1002004", "high_price", false);
        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productSearchRepository.search(cond, of);
        List<ProductPreviewDto> content = dto.getContent();
        Pageable pageable = dto.getPageable();

        //then
        assertThat(content.size()).isEqualTo(5);
        assertThat(content.get(0).getPrice()).isEqualTo(342834);
        assertThat(content.get(4).getPrice()).isEqualTo(8954);
    }

    @Test
    public void 낮은가격순_조회(){
        //given
        ProductSearchCond cond = new ProductSearchCond("1002004", "low_price", false);
        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productSearchRepository.search(cond, of);
        List<ProductPreviewDto> content = dto.getContent();
        Pageable pageable = dto.getPageable();

        //then
        assertThat(content.size()).isEqualTo(5);
        assertThat(content.get(0).getPrice()).isEqualTo(8954);
        assertThat(content.get(4).getPrice()).isEqualTo(342834);
    }

    @Test
    public void 카테고리_조회(){
        //given
        ProductSearchCond cond = new ProductSearchCond("1002005", "low_price", false);
        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productSearchRepository.search(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getImg()).isEqualTo("https://image06");
    }

    @Test
    public void 최신순_조회(){
        //given
        ProductSearchCond cond = new ProductSearchCond("1002004", "latest", false);
        PageRequest of = PageRequest.of(0, 5);

        //when
        Page<ProductPreviewDto> dto = productSearchRepository.search(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(5);
        assertThat(content.get(0).getImg()).isEqualTo("https://image05");
        assertThat(content.get(4).getImg()).isEqualTo("https://image01");
    }

    @Test
    public void 팔린것만_조회(){
        //given
        ProductSearchCond cond = new ProductSearchCond("1002001", "latest", true);
        PageRequest of = PageRequest.of(0, 3);

        //when
        Page<ProductPreviewDto> dto = productSearchRepository.search(cond, of);
        List<ProductPreviewDto> content = dto.getContent();
        int size = dto.getSize();

        //then
        assertThat(content.get(0).getTitle()).isEqualTo("test-title-SoldOut");
        assertThat(content.size()).isEqualTo(1);
    }

    @Test
    public void Sold옵션_null(){
        //given
        ProductSearchCond cond = new ProductSearchCond("1002001", "latest", null);
        PageRequest of = PageRequest.of(0, 3);

        //when
        Page<ProductPreviewDto> dto = productSearchRepository.search(cond, of);
        List<ProductPreviewDto> content = dto.getContent();

        //then
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getTitle()).isEqualTo("test-title-SoldOut");
        assertThat(content.get(1).getTitle()).isEqualTo("test-title-OnSale");
    }
}