package rePashion.server.domain.product.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import rePashion.server.domain.product.dto.ProductDto;
import rePashion.server.domain.product.dto.ProductPreviewDto;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.user.model.*;
import rePashion.server.domain.user.repository.UserProductRepository;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.common.config.JpaQueryFactoryConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({MyProductRepository.class, JpaQueryFactoryConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MyProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyProductRepository myProductRepository;

    User seller;
    User other;

    List<Product> products;

    @BeforeEach
    public void initDB(){
        ArrayList<BasicInfo> basicInfos = makeBasicInfos();
        products = getProducts(basicInfos);
        saveSeller();
        saveOther();
        mappingProductToSeller();
        mappingProductToOther();
        productStatusUpdate();
    }

    private void productStatusUpdate() {
        // product 2번 상태를 판매완료로 변경
        Product product1 = products.get(2);
        productRepository.updateStatus(product1.getId());

        // product 4번 상태를 판매완료로 변경
        Product product2 = products.get(4);
        productRepository.updateStatus(product2.getId());
    }

    @Test
    public void 내가_판매중인_상품들_조회(){
        //given
        PageRequest of = PageRequest.of(0, 5);
        //when
        Page<ProductPreviewDto> dto = myProductRepository.getBySeller(seller,false, of);
        List<ProductPreviewDto> content = dto.getContent();
        //then
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0)).extracting("title").isEqualTo("title1"); // 최신순 조회
        assertThat(content.get(1)).extracting("title").isEqualTo("title0");
    }

    @Test
    public void 내가_판매완료한_상품들_조회(){
        //given
        PageRequest of = PageRequest.of(0, 5);
        //when
        Page<ProductPreviewDto> dto = myProductRepository.getBySeller(seller,true, of);
        List<ProductPreviewDto> content = dto.getContent();
        //then
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0)).extracting("title").isEqualTo("title2");
    }

    private void mappingProductToSeller() {
        for(int i =0; i<3; i++){
            UserProduct userProduct = new UserProduct(PurchaseStatus.Seller);
            userProduct.changeUserAndProduct(seller, products.get(i));
            userProductRepository.save(userProduct);
        }
    }

    private void mappingProductToOther(){
        for(int i =3; i<5; i++){
            UserProduct userProduct = new UserProduct(PurchaseStatus.Seller);
            userProduct.changeUserAndProduct(other, products.get(i));
            userProductRepository.save(userProduct);
        }
    }

    private void saveOther() {
        User user = new User("other@test.com", "other");
        other = userRepository.save(user);
    }

    private void saveSeller() {
        User user = new User("seller@test.com", "seller");
        seller = userRepository.save(user);
    }

    private List<Product> getProducts(ArrayList<BasicInfo> basicInfos) {
        return basicInfos.stream().map(o -> {
            Product product = Product.builder().basicInfo(o).build();
            return productRepository.save(product);
        }).collect(Collectors.toList());
    }


    private ArrayList<BasicInfo> makeBasicInfos() {
        ArrayList<BasicInfo> basicInfos = new ArrayList<>();
        for(int i=0; i < 5; i++){
            basicInfos.add(
                    BasicInfo.builder()
                            .title("title" + i)
                            .contact("test02@test.com")
                            .category("1001001")
                            .brand("brand02")
                            .thumbnailImage("https://image02")
                            .price(200001)
                            .isIncludeDelivery(true)
                            .size("XS")
                            .build()
                    );
        }
        return basicInfos;
    }
}