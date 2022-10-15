package rePashion.server.domain.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;
import rePashion.server.domain.product.dto.ProductRequestBody;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.repository.ProductRepository;
import rePashion.server.domain.product.service.ProductService;
import rePashion.server.domain.user.model.Role;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.model.UserAuthority;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.jwt.impl.AccessTokenProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void 정상적으로_저장하기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductRequestBody.BasicInfo basicInfo = new ProductRequestBody.BasicInfo("나이키 프린팅 티셔츠", "100304", "나이키");
        ProductRequestBody.AdditionalInfo additionalInfo = new ProductRequestBody.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductRequestBody.SellerNote sellerNote = new ProductRequestBody.SellerNote("none", "normal", 130, "chubby", "all", "normal");
        ProductRequestBody.Style style = new ProductRequestBody.Style("feminin", "Blue", "fur");
        ProductRequestBody.Measure measure = new ProductRequestBody.Measure(120, 10, 10, 0, 1, 3, 4, 5);
        ProductRequestBody productRequestBody = new ProductRequestBody(
                imgList,
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                measure,
                "매우 깨끗한 옷입니다",
                "top"
        );
        String body = new ObjectMapper().writeValueAsString(productRequestBody);

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
        User user = new User("test@test.com", "", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductRequestBody.BasicInfo basicInfo = new ProductRequestBody.BasicInfo("나이키 프린팅 티셔츠", "100304", "나이키");
        ProductRequestBody.AdditionalInfo additionalInfo = new ProductRequestBody.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductRequestBody.SellerNote sellerNote = new ProductRequestBody.SellerNote("none", "normal", 130, "chubby", "all", "normal");
        ProductRequestBody.Style style = new ProductRequestBody.Style("feminin", "Blue", "fur");
        ProductRequestBody.Measure measure = new ProductRequestBody.Measure(120, 10, 10, 0, 1, 3, 4, 5);
        ProductRequestBody productRequestBody = new ProductRequestBody(
                imgList,
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                measure,
                "매우 깨끗한 옷입니다",
                "top"
        );

        //when
        Product savedProduct = productService.save(savedUser, productRequestBody);

        // 바뀐 Request 만들기
        ProductRequestBody.BasicInfo changedBasicInfo = new ProductRequestBody.BasicInfo("아디다스 프린팅 티셔츠", "100304", "아디다스");
        ProductRequestBody changedProductRequestBody = new ProductRequestBody(
                imgList,
                "contact.com",
                changedBasicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                measure,
                "매우 깨끗한 옷입니다",
                "top"
        );
        String body = new ObjectMapper().writeValueAsString(changedProductRequestBody);

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
        User user = new User("test@test.com", "", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductRequestBody.BasicInfo basicInfo = new ProductRequestBody.BasicInfo("나이키 프린팅 티셔츠", "100304", "나이키");
        ProductRequestBody.AdditionalInfo additionalInfo = new ProductRequestBody.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductRequestBody.SellerNote sellerNote = new ProductRequestBody.SellerNote("none", "normal", 130, "chubby", "all", "normal");
        ProductRequestBody.Style style = new ProductRequestBody.Style("feminin", "Blue", "fur");
        ProductRequestBody.Measure measure = new ProductRequestBody.Measure(120, 10, 10, 0, 1, 3, 4, 5);
        ProductRequestBody productRequestBody = new ProductRequestBody(
                imgList,
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                measure,
                "매우 깨끗한 옷입니다",
                "top"
        );
        Product savedProduct = productService.save(savedUser, productRequestBody);
        String body = new ObjectMapper().writeValueAsString(productRequestBody);

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
    public void 타인이_변경하려고_하면_예외_발생() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "", "hi");
        User savedUser = userRepository.save(user);

        // Request 만들기
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("image1.com");
        imgList.add("image2.com");
        imgList.add("image3.com");

        ProductRequestBody.BasicInfo basicInfo = new ProductRequestBody.BasicInfo("나이키 프린팅 티셔츠", "100304", "나이키");
        ProductRequestBody.AdditionalInfo additionalInfo = new ProductRequestBody.AdditionalInfo("2020-04-01", "온라인 매장");
        ProductRequestBody.SellerNote sellerNote = new ProductRequestBody.SellerNote("none", "normal", 130, "chubby", "all", "normal");
        ProductRequestBody.Style style = new ProductRequestBody.Style("feminin", "Blue", "fur");
        ProductRequestBody.Measure measure = new ProductRequestBody.Measure(120, 10, 10, 0, 1, 3, 4, 5);
        ProductRequestBody productRequestBody = new ProductRequestBody(
                imgList,
                "contact.com",
                basicInfo,
                10000,
                true,
                "XL",
                additionalInfo,
                sellerNote,
                style,
                measure,
                "매우 깨끗한 옷입니다",
                "top"
        );
        Product savedProduct = productService.save(savedUser, productRequestBody);
        String body = new ObjectMapper().writeValueAsString(productRequestBody);

        //when
        User otherUser = new User("other@test.com", "", "hi");
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