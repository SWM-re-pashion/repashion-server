package rePashion.server.domain.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import rePashion.server.domain.product.dto.MeasureDto;
import rePashion.server.domain.product.dto.ProductDto;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.measure.entity.Measure;
import rePashion.server.domain.product.repository.ProductRepository;
import rePashion.server.domain.product.service.ProductService;
import rePashion.server.domain.user.model.Role;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.model.UserAuthority;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.jwt.impl.AccessTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Value("${auth.jwt.access.header}")
    private String header;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessTokenProvider accessTokenProvider;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductController productController;

    @Test
    public void 정상적으로_저장하기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductDto.BasicInfo basicInfo = new ProductDto.BasicInfo("나이키 프린팅 티셔츠", "100304", "나이키");
        ProductDto.AdditionalInfo additionalInfo = new ProductDto.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductDto.SellerNote sellerNote = new ProductDto.SellerNote("none", "normal", 130, "chubby", "all", "normal");
        ProductDto.Style style = new ProductDto.Style("feminin", "Blue", "fur");
        MeasureDto measure = new MeasureDto();
        measure.setLength(120);
        measure.setShoulderWidth(10);
        measure.setChestSection(15);
        measure.setSleeveLength(20);
        ProductDto productDto = new ProductDto(
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                "매우 깨끗한 옷입니다",
                "top"
        );
        productDto.changeImgList(imgList);
        productDto.changeMeasure(measure);
        String body = new ObjectMapper().writeValueAsString(productDto);

        //when
        //then
        mvc.perform(post("/api/product").header(header, parsedAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        List<Product> findProducts = productRepository.findAll();
        Assertions.assertThat(findProducts.get(0).getBasicInfo().getTitle()).isEqualTo("나이키 프린팅 티셔츠");
        Assertions.assertThat(findProducts.get(0).getBasicInfo().getBrand()).isEqualTo("나이키");
    }

    @Test
    public void 정상적으로_변경하기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductDto.BasicInfo basicInfo = new ProductDto.BasicInfo("나이키 프린팅 티셔츠", "100304", "나이키");
        ProductDto.AdditionalInfo additionalInfo = new ProductDto.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductDto.SellerNote sellerNote = new ProductDto.SellerNote("none", "normal", 130, "chubby", "all", "normal");
        ProductDto.Style style = new ProductDto.Style("feminin", "Blue", "fur");
        MeasureDto measure = new MeasureDto();
        measure.setLength(120);
        measure.setShoulderWidth(10);
        measure.setChestSection(15);
        measure.setSleeveLength(20);
        ProductDto productDto = new ProductDto(
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                "매우 깨끗한 옷입니다",
                "top"
        );
        productDto.changeImgList(imgList);
        productDto.changeMeasure(measure);
        //when
        Product savedProduct = productService.save(savedUser, productDto);

        // 바뀐 Request 만들기
        ProductDto.BasicInfo changedBasicInfo = new ProductDto.BasicInfo("아디다스 프린팅 티셔츠", "100304", "아디다스");
        ProductDto changedProductDto = new ProductDto(
                "contact.com",
                changedBasicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                "매우 깨끗한 옷입니다",
                "top"
        );
        changedProductDto.changeImgList(imgList);
        changedProductDto.changeMeasure(measure);
        String body = new ObjectMapper().writeValueAsString(changedProductDto);

        //then
        mvc.perform(put("/api/product/" + savedProduct.getId()).header(header, parsedAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((body)))
                .andExpect(status().isOk());

        List<Product> findProducts = productRepository.findAll();
        Assertions.assertThat(findProducts.get(0).getBasicInfo().getTitle()).isEqualTo("아디다스 프린팅 티셔츠");
        Assertions.assertThat(findProducts.get(0).getBasicInfo().getBrand()).isEqualTo("아디다스");
        productRepository.deleteAll();
    }

    @Test
    @Order(3)
    public void 정상적으로_삭제하기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductDto.BasicInfo basicInfo = new ProductDto.BasicInfo("나이키 프린팅 티셔츠", "100304", "나이키");
        ProductDto.AdditionalInfo additionalInfo = new ProductDto.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductDto.SellerNote sellerNote = new ProductDto.SellerNote("none", "normal", 130, "chubby", "all", "normal");
        ProductDto.Style style = new ProductDto.Style("feminin", "Blue", "fur");
        MeasureDto measure = new MeasureDto();
        measure.setLength(120);
        measure.setShoulderWidth(10);
        measure.setChestSection(15);
        measure.setSleeveLength(20);
        ProductDto productDto = new ProductDto(
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                "매우 깨끗한 옷입니다",
                "top"
        );
        productDto.changeImgList(imgList);
        productDto.changeMeasure(measure);
        Product savedProduct = productService.save(savedUser, productDto);
        String body = new ObjectMapper().writeValueAsString(productDto);

        //when

        //then
        mvc.perform(delete("/api/product/" + savedProduct.getId()).header(header, parsedAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((body)))
                .andExpect(status().isNoContent());

        Optional<Product> findProduct = productRepository.findById(savedProduct.getId());
        Assertions.assertThat(findProduct).isEqualTo(Optional.empty());
    }

    @Test
    @Order(4)
    public void 정상적으로_정보_가져오기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductDto.BasicInfo basicInfo = new ProductDto.BasicInfo("나이키 프린팅 티셔츠", "100304", "나이키");
        ProductDto.AdditionalInfo additionalInfo = new ProductDto.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductDto.SellerNote sellerNote = new ProductDto.SellerNote("none", "normal", 130, "chubby", "all", "normal");
        ProductDto.Style style = new ProductDto.Style("feminin", "Blue", "fur");
        MeasureDto measure = new MeasureDto();
        measure.setLength(120);
        measure.setShoulderWidth(10);
        measure.setChestSection(15);
        measure.setSleeveLength(20);

        ProductDto productDto = new ProductDto(
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                "매우 깨끗한 옷입니다",
                "top"
        );
        productDto.changeImgList(imgList);
        productDto.changeMeasure(measure);
        Product savedProduct = productService.save(savedUser, productDto);
        //when

        //then
        mvc.perform(get("/api/product/" + savedProduct.getId()).header(header, parsedAccessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.basicInfo.title", is("나이키 프린팅 티셔츠")))
                .andExpect(jsonPath("$.data.additionalInfo.purchaseTime", is("2020-04-01")))
                .andExpect(jsonPath("$.data.measure.length", is(120)))
                .andExpect(jsonPath("$.data.measure.sleeveLength", is(20)));
    }

    @Test
    @Order(5)
    public void 내가_올린_물건_정상적으로_디테일_정보_가져오기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductDto.BasicInfo basicInfo = new ProductDto.BasicInfo("나이키 프린팅 티셔츠", "1001001", "나이키");
        ProductDto.AdditionalInfo additionalInfo = new ProductDto.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductDto.SellerNote sellerNote = new ProductDto.SellerNote("none", "normal", 130, "chubby", "waist", "normal");
        ProductDto.Style style = new ProductDto.Style("feminine", "Blue", "fur");
        MeasureDto measure = new MeasureDto();
        measure.setLength(120);
        measure.setShoulderWidth(10);
        measure.setChestSection(15);
        measure.setSleeveLength(20);

        ProductDto productDto = new ProductDto(
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                "매우 깨끗한 옷입니다",
                "top"
        );
        productDto.changeImgList(imgList);
        productDto.changeMeasure(measure);
        Product savedProduct = productService.save(savedUser, productDto);
        productController.getDetail(savedUser.getId(), savedProduct.getId());
        //when

        //then
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()).header(header, parsedAccessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isMe", is(true)))
                .andExpect(jsonPath("$.data.status", is(false)))
                .andExpect(jsonPath("$.data.sellerInfo.profileImg", is(savedUser.getProfile())))
                .andExpect(jsonPath("$.data.sellerInfo.nickname", is("hi")))
                .andExpect(jsonPath("$.data.basic.title", is("나이키 프린팅 티셔츠")))
                .andExpect(jsonPath("$.data.sellerInfo.image[0]", is("image1.com")))
                .andExpect(jsonPath("$.data.sellerInfo.image[1]", is("image2.com")))
                .andExpect(jsonPath("$.data.sellerInfo.image[2]", is("image3.com")))
                .andExpect(jsonPath("$.data.basic.classification", is("상의/탑")))
                .andExpect(jsonPath("$.data.basic.productInfo", is("남성/XL")))
                .andExpect(jsonPath("$.data.basic.brand", is("나이키")))
                .andExpect(jsonPath("$.data.basic.styleInfo", is("fur/Blue/페미닌")))
                .andExpect(jsonPath("$.data.sellerNotice.condition", is("거의 없음")))
                .andExpect(jsonPath("$.data.sellerNotice.pollution", is("보통")))
                .andExpect(jsonPath("$.data.sellerNotice.height", is(130)))
                .andExpect(jsonPath("$.data.sellerNotice.length", is("허리")))
                .andExpect(jsonPath("$.data.sellerNotice.bodyShape", is("통통")))
                .andExpect(jsonPath("$.data.sellerNotice.fit", is("보통")))
                .andExpect(jsonPath("$.data.measure.length", is(120)))
                .andExpect(jsonPath("$.data.measure.shoulderWidth", is(10)))
                .andExpect(jsonPath("$.data.measure.chestSection", is(15)))
                .andExpect(jsonPath("$.data.measure.sleeveLength", is(20)))
                .andExpect(jsonPath("$.data.sellerNotice.purchaseTime", is("2020-04-01")))
                .andExpect(jsonPath("$.data.sellerNotice.purchasePlace", is("온라인 매장")))
                .andExpect(jsonPath("$.data.opinion", is("매우 깨끗한 옷입니다")))
                .andExpect(jsonPath("$.data.like", is(0)))
                .andExpect(jsonPath("$.data.view", is(0)));
    }

    @Test
    @Order(6)
    public void 내가_올리지_않은_물건_정상적으로_디테일_정보_가져오기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        User seller = new User("buyer@test.com", "hi");
        User savedSeller = userRepository.save(seller);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductDto.BasicInfo basicInfo = new ProductDto.BasicInfo("나이키 프린팅 티셔츠", "1001001", "나이키");
        ProductDto.AdditionalInfo additionalInfo = new ProductDto.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductDto.SellerNote sellerNote = new ProductDto.SellerNote("none", "normal", 130, "chubby", "waist", "normal");
        ProductDto.Style style = new ProductDto.Style("feminine", "Blue", "fur");
        MeasureDto measure = new MeasureDto();
        measure.setLength(120);
        measure.setShoulderWidth(10);
        measure.setChestSection(15);
        measure.setSleeveLength(20);

        ProductDto productDto = new ProductDto(
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                "매우 깨끗한 옷입니다",
                "top"
        );
        productDto.changeImgList(imgList);
        productDto.changeMeasure(measure);
        Product savedProduct = productService.save(savedSeller, productDto);
        productController.getDetail(savedUser.getId(), savedProduct.getId());
        //when

        //then
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()).header(header, parsedAccessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isMe", is(false)));
    }

    @Test
    public void 타인이_변경하려고_하면_예외_발생() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        User savedUser = userRepository.save(user);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductDto.BasicInfo basicInfo = new ProductDto.BasicInfo("나이키 프린팅 티셔츠", "100304", "나이키");
        ProductDto.AdditionalInfo additionalInfo = new ProductDto.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductDto.SellerNote sellerNote = new ProductDto.SellerNote("none", "normal", 130, "chubby", "all", "normal");
        ProductDto.Style style = new ProductDto.Style("feminin", "Blue", "fur");
        MeasureDto measure = new MeasureDto();
        measure.setLength(120);
        measure.setShoulderWidth(10);
        measure.setChestSection(15);
        measure.setSleeveLength(20);
        ProductDto productDto = new ProductDto(
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                "매우 깨끗한 옷입니다",
                "top"
        );
        productDto.changeImgList(imgList);
        productDto.changeMeasure(measure);
        Product savedProduct = productService.save(savedUser, productDto);
        String body = new ObjectMapper().writeValueAsString(productDto);

        //when
        User otherUser = new User("other@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(otherUser);
        User savedOtherUser = userRepository.save(otherUser);
        String parsedToken = accessTokenProvider.parse(savedOtherUser);

        //then
        mvc.perform(put("/api/product/" + savedProduct.getId()).header(header, parsedToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((body)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(6)
    public void 외부_사람이_Detail_조회() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User seller = new User("buyer@test.com", "hi");
        User savedSeller = userRepository.save(seller);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductDto.BasicInfo basicInfo = new ProductDto.BasicInfo("나이키 프린팅 티셔츠", "1001001", "나이키");
        ProductDto.AdditionalInfo additionalInfo = new ProductDto.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductDto.SellerNote sellerNote = new ProductDto.SellerNote("none", "normal", 130, "chubby", "waist", "normal");
        ProductDto.Style style = new ProductDto.Style("feminine", "Blue", "fur");
        MeasureDto measure = new MeasureDto();
        measure.setLength(120);
        measure.setShoulderWidth(10);
        measure.setChestSection(15);
        measure.setSleeveLength(20);

        ProductDto productDto = new ProductDto(
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                "매우 깨끗한 옷입니다",
                "top"
        );
        productDto.changeImgList(imgList);
        productDto.changeMeasure(measure);
        Product savedProduct = productService.save(savedSeller, productDto);
        //when

        //then
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isMe", is(false)))
                .andExpect(jsonPath("$.data.isSoldOut", is(false)))
                .andExpect(jsonPath("$.data.sellerInfo.profileImg", is(savedSeller.getProfile())))
                .andExpect(jsonPath("$.data.sellerInfo.nickname", is("hi")))
                .andExpect(jsonPath("$.data.basic.title", is("나이키 프린팅 티셔츠")))
                .andExpect(jsonPath("$.data.sellerInfo.image[0]", is("image1.com")))
                .andExpect(jsonPath("$.data.sellerInfo.image[1]", is("image2.com")))
                .andExpect(jsonPath("$.data.sellerInfo.image[2]", is("image3.com")))
                .andExpect(jsonPath("$.data.basic.classification", is("상의/탑")))
                .andExpect(jsonPath("$.data.basic.productInfo", is("남성/XL")))
                .andExpect(jsonPath("$.data.basic.brand", is("나이키")))
                .andExpect(jsonPath("$.data.basic.styleInfo", is("fur/Blue/페미닌")))
                .andExpect(jsonPath("$.data.sellerNotice.condition", is("거의 없음")))
                .andExpect(jsonPath("$.data.sellerNotice.pollution", is("보통")))
                .andExpect(jsonPath("$.data.sellerNotice.height", is(130)))
                .andExpect(jsonPath("$.data.sellerNotice.length", is("허리")))
                .andExpect(jsonPath("$.data.sellerNotice.bodyShape", is("통통")))
                .andExpect(jsonPath("$.data.sellerNotice.fit", is("노멀")))
                .andExpect(jsonPath("$.data.measure.length", is(120)))
                .andExpect(jsonPath("$.data.measure.shoulderWidth", is(10)))
                .andExpect(jsonPath("$.data.measure.chestSection", is(15)))
                .andExpect(jsonPath("$.data.measure.sleeveLength", is(20)))
                .andExpect(jsonPath("$.data.sellerNotice.purchaseTime", is("2020-04-01")))
                .andExpect(jsonPath("$.data.sellerNotice.purchasePlace", is("온라인 매장")))
                .andExpect(jsonPath("$.data.opinion", is("매우 깨끗한 옷입니다")))
                .andExpect(jsonPath("$.data.like", is(0)))
                .andExpect(jsonPath("$.data.view", is(0)));
    }


    @Test
    @Order(7)
    public void 방문하지_않았던_유저가_방문시에는_조회수가_오른다() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User seller = new User("seller@test.com", "hi");
        User savedSeller = userRepository.save(seller);
        ProductDto productDto = makeProduct();
        Product savedProduct = productService.save(savedSeller, productDto);

        // 다른 유저 만들기
        User visitor = new User("visitor@test.com", "hihi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(visitor);
        User savedVisitor = userRepository.save(visitor);
        String parsedToken = accessTokenProvider.parse(savedVisitor);

        //when

        // 다른 유저

        // 외부 유저로 조회하기

        //then
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()).header(header, parsedToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isMe", is(false)))
                .andExpect(jsonPath("$.data.view", is(1)));
    }
    @Test
    @Order(8)
    public void 외부_유저가_접근_시에는_조회수가_오르지_않는다() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User seller = new User("seller@test.com", "hi");
        User savedSeller = userRepository.save(seller);
        ProductDto productDto = makeProduct();
        Product savedProduct = productService.save(savedSeller, productDto);

        //when

        // 외부 유저 만들기
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()));
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()));
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()));
        // 외부 유저로 조회하기

        //then
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isMe", is(false)))
                .andExpect(jsonPath("$.data.view", is(0)));
    }
    @Test
    @Order(9)
    public void 이미_방문했던_유저가_방문_시에는_조회수가_오르지_않는다() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User seller = new User("seller@test.com", "hi");
        User savedSeller = userRepository.save(seller);
        ProductDto productDto = makeProduct();
        Product savedProduct = productService.save(savedSeller, productDto);

        //when

        // 외부 유저 만들기
        User visitor = new User("visitor@test.com", "hihi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(visitor);
        User savedVisitor = userRepository.save(visitor);
        String parsedToken = accessTokenProvider.parse(savedVisitor);
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()).header(header, parsedToken));
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()).header(header, parsedToken));
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()).header(header, parsedToken));

        // 외부 유저로 조회하기

        //then
        mvc.perform(get("/api/product/detail/" + savedProduct.getId()).header(header, parsedToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isMe", is(false)))
                .andExpect(jsonPath("$.data.view", is(1)));
    }

    private ProductDto makeProduct(){
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductDto.BasicInfo basicInfo = new ProductDto.BasicInfo("나이키 프린팅 티셔츠", "1001001", "나이키");
        ProductDto.AdditionalInfo additionalInfo = new ProductDto.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductDto.SellerNote sellerNote = new ProductDto.SellerNote("none", "normal", 130, "chubby", "waist", "normal");
        ProductDto.Style style = new ProductDto.Style("feminine", "Blue", "fur");
        MeasureDto measure = new MeasureDto();
        measure.setLength(120);
        measure.setShoulderWidth(10);
        measure.setChestSection(15);
        measure.setSleeveLength(20);

        ProductDto productDto = new ProductDto(
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                "매우 깨끗한 옷입니다",
                "top"
        );
        productDto.changeImgList(imgList);
        productDto.changeMeasure(measure);
        return productDto;
    }
}