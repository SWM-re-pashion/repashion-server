package rePashion.server.domain.product.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.ProductRecommend;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.product.resources.request.Condition;
import rePashion.server.domain.statics.model.filter.Order;
import rePashion.server.global.common.config.JpaQueryFactoryConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({ProductRecommendCustomRepository.class, JpaQueryFactoryConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRecommendCustomRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRecommendRepository productRecommendRepository;

    @Autowired
    private ProductRecommendCustomRepository productRecommendCustomRepository;

    private List<ProductRecommend> recommends = new ArrayList<>();

    @BeforeEach
    public void initDB(){
        ArrayList<BasicInfo> basicInfos = makeBasicInfos();
        List<Product> products = getProducts(basicInfos);
        makeProductRecommends(products);
    }

    @Test
    public void 판매완료_숨기지_않고_최신순_조회하기(){
        //given
        Condition.Recommend cond = new Condition.Recommend();
        cond.setOrder(Order.latest);
        cond.setHideSold(false);
        PageRequest of = PageRequest.of(0, 3);
        //when
        Page<ProductRecommend> productRecommends = productRecommendCustomRepository.get(cond, of);
        List<ProductRecommend> content = productRecommends.getContent();
        //then
        assertThat(content.get(0).getProduct().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(0).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title4");
        assertThat(content.get(1).getProduct().getBasicInfo()).extracting("title").isEqualTo("title2");
        assertThat(content.get(1).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(2).getProduct().getBasicInfo()).extracting("title").isEqualTo("title1");
        assertThat(content.get(2).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
    }

    @Test
    public void 판매완료_숨기지_않고_낮은_가격순_조회하기(){
        //given
        Condition.Recommend cond = new Condition.Recommend();
        cond.setOrder(Order.low_price);
        cond.setHideSold(false);
        PageRequest of = PageRequest.of(0, 5);
        //when
        Page<ProductRecommend> productRecommends = productRecommendCustomRepository.get(cond, of);
        List<ProductRecommend> content = productRecommends.getContent();
        //then
        assertThat(content.get(0).getProduct().getBasicInfo()).extracting("title").isEqualTo("title0");
        assertThat(content.get(0).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title1");
        assertThat(content.get(1).getProduct().getBasicInfo()).extracting("title").isEqualTo("title0");
        assertThat(content.get(1).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title2");
        assertThat(content.get(2).getProduct().getBasicInfo()).extracting("title").isEqualTo("title1");
        assertThat(content.get(2).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
    }

    @Test
    public void 판매완료_숨기지_않고_높은_가격순_조회하기(){
        //given
        Condition.Recommend cond = new Condition.Recommend();
        cond.setOrder(Order.high_price);
        cond.setHideSold(false);
        PageRequest of = PageRequest.of(0, 5);
        //when
        Page<ProductRecommend> productRecommends = productRecommendCustomRepository.get(cond, of);
        List<ProductRecommend> content = productRecommends.getContent();
        //then
        assertThat(content.get(0).getProduct().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(0).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title4");
        assertThat(content.get(1).getProduct().getBasicInfo()).extracting("title").isEqualTo("title2");
        assertThat(content.get(1).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(2).getProduct().getBasicInfo()).extracting("title").isEqualTo("title1");
        assertThat(content.get(2).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
    }

    @Test
    public void 판매완료_숨기지_않고_조회수순_조회하기(){}

    @Test
    public void 판매완료_숨기지_않고_좋아요순_조회하기(){}

    @Test
    public void 판매완료_숨기고_최신순_조회하기(){    // 첫번쨰 product를 기준으로 최신순 조회
        //given
        Condition.Recommend cond = new Condition.Recommend();
        cond.setOrder(Order.latest);
        cond.setHideSold(true);
        PageRequest of = PageRequest.of(0, 3);
        //when
        Page<ProductRecommend> productRecommends = productRecommendCustomRepository.get(cond, of);
        List<ProductRecommend> content = productRecommends.getContent();
        //then
        assertThat(content.get(0).getProduct().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(0).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title4");
        assertThat(content.get(1).getProduct().getBasicInfo()).extracting("title").isEqualTo("title2");
        assertThat(content.get(1).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(2).getProduct().getBasicInfo()).extracting("title").isEqualTo("title1");
        assertThat(content.get(2).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
    }

    @Test
    public void 판매완료_숨기고_낮은_가격순_조회하기(){
        //given
        Condition.Recommend cond = new Condition.Recommend();
        cond.setOrder(Order.low_price);
        cond.setHideSold(true);
        PageRequest of = PageRequest.of(0, 3);
        //when
        Page<ProductRecommend> productRecommends = productRecommendCustomRepository.get(cond, of);
        List<ProductRecommend> content = productRecommends.getContent();
        //then
        assertThat(content.get(0).getProduct().getBasicInfo()).extracting("title").isEqualTo("title1");
        assertThat(content.get(0).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(1).getProduct().getBasicInfo()).extracting("title").isEqualTo("title2");
        assertThat(content.get(1).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(2).getProduct().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(2).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title4");
    }

    @Test
    public void 판매완료_숨기고_높은_가격순_조회하기(){
        //given
        Condition.Recommend cond = new Condition.Recommend();
        cond.setOrder(Order.high_price);
        cond.setHideSold(true);
        PageRequest of = PageRequest.of(0, 3);
        //when
        Page<ProductRecommend> productRecommends = productRecommendCustomRepository.get(cond, of);
        List<ProductRecommend> content = productRecommends.getContent();
        //then
        assertThat(content.get(0).getProduct().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(0).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title4");
        assertThat(content.get(1).getProduct().getBasicInfo()).extracting("title").isEqualTo("title2");
        assertThat(content.get(1).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(2).getProduct().getBasicInfo()).extracting("title").isEqualTo("title1");
        assertThat(content.get(2).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
    }

    @Test
    public void 판매완료_숨기고_조회수순_조회하기(){}

    @Test
    public void 판매완료_숨기고_좋아요순_조회하기(){}

    @Test
    public void 남성_데이터와_공용_데이터를_조회하기(){
        //given
        Condition.Recommend cond = new Condition.Recommend();
        cond.setOrder(Order.latest);
        cond.setCategory((byte) 1);
        cond.setHideSold(false);
        PageRequest of = PageRequest.of(0, 3);
        //when
        Page<ProductRecommend> productRecommends = productRecommendCustomRepository.get(cond, of);
        List<ProductRecommend> content = productRecommends.getContent();
        //then
        assertThat(content.get(0).getProduct().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(0).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title4");
        assertThat(content.get(1).getProduct().getBasicInfo()).extracting("title").isEqualTo("title1");
        assertThat(content.get(1).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(2).getProduct().getBasicInfo()).extracting("title").isEqualTo("title0");
        assertThat(content.get(2).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title1");
    }

    @Test
    public void 여성_데이터와_공용_데이터를_조회하기(){
        //given
        Condition.Recommend cond = new Condition.Recommend();
        cond.setOrder(Order.latest);
        cond.setCategory((byte) 2);
        cond.setHideSold(false);
        PageRequest of = PageRequest.of(0, 3);
        //when
        Page<ProductRecommend> productRecommends = productRecommendCustomRepository.get(cond, of);
        List<ProductRecommend> content = productRecommends.getContent();
        //then
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getProduct().getBasicInfo()).extracting("title").isEqualTo("title2");
        assertThat(content.get(0).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title3");
        assertThat(content.get(1).getProduct().getBasicInfo()).extracting("title").isEqualTo("title0");
        assertThat(content.get(1).getAssociation().getBasicInfo()).extracting("title").isEqualTo("title2");
    }
    private void makeProductRecommends(List<Product> products) {
        int [][] collections = {{0,1}, {0,2}, {1,3}, {3,4}, {2,3}}; // {공용,남성} {공용,여성} {남성,공용}, {공용,남성}, {여성,공용}
        for(int i =0; i < 5; i++){
            saveRecommendPair(products.get(collections[i][0]), products.get(collections[i][1]));
        }
    }

    private void saveRecommendPair(Product product1, Product product2){
        productRecommendRepository.save(productRecommendRepository.save(new ProductRecommend(product1, product2)));
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
            String category = setCategory(i);
            BasicInfo info = BasicInfo.builder()
                    .title("title" + i)
                    .contact("test02@test.com")
                    .category(category)
                    .brand("brand02")
                    .thumbnailImage("https://image02")
                    .price(20000 + i)
                    .isIncludeDelivery(true)
                    .size("XS")
                    .build();
            if(i==0) info.changeStatus();   // 0만 status를 true로
            basicInfos.add(info);
        }
        return basicInfos;
    }

    private String setCategory(int i){
        if(i%3 == 0) return "3001001";  // 공용
        else if(i%3 == 1)   return "1001001";   // 남성
        else return "20010001"; // 여성
    }
}