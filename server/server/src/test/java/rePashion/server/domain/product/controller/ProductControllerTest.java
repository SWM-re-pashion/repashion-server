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

    @Test
    @Rollback(value = false)
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
}