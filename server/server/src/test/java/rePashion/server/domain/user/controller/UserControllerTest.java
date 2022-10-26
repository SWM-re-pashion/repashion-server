package rePashion.server.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.product.repository.ProductRepository;
import rePashion.server.domain.user.model.*;
import rePashion.server.domain.user.repository.UserProductRepository;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.jwt.impl.AccessTokenProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Value("${auth.jwt.access.header}")
    private String header;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    @Autowired
    private AccessTokenProvider accessTokenProvider;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserController myUserController;

    private ArrayList<Product> productBuyList = new ArrayList<>();

    private ArrayList<Product> productSellList = new ArrayList<>();

    @BeforeEach
    public void initProductDb(){
        for(int i =0; i < 5; i++){
            productBuyList.add(createProduct());
        }

        for(int i =0; i < 4; i++){
            productSellList.add(createProduct());
        }
    }

    private Product createProduct() {

        BasicInfo basicInfo = BasicInfo.builder()
                .title("title01")
                .contact("test01@test.com")
                .category("1001001")
                .brand("brand01")
                .thumbnailImage("https://image01")
                .price(200000)
                .isIncludeDelivery(true)
                .size("XS")
                .build();

        Product product = Product.builder().basicInfo(basicInfo).build();
        return productRepository.save(product);
    }

    @Test
    public void 정상적인_내정보_받기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        // User가 판매, 구매중인 상품들 만들기
        productBuyList.forEach(product -> {
            UserProduct userProduct = new UserProduct(PurchaseStatus.Buyer);
            userProduct.changeUserAndProduct(savedUser, product);
            userProductRepository.save(userProduct);
        });

        productSellList.forEach(product -> {
            UserProduct userProduct = new UserProduct(PurchaseStatus.Seller);
            userProduct.changeUserAndProduct(savedUser, product);
            userProductRepository.save(userProduct);
        });

        //when
        //then
        mvc.perform(get("/api/user/my").header(header, parsedAccessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(savedUser.getNickName())))
                .andExpect(jsonPath("$.data.profileImage", is(savedUser.getProfile())))
                .andExpect(jsonPath("$.data.totalCount", is(9)));
    }

    @Test
    public void 정상적인_닉네임_수정() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "originalNickName");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        Map<String, String> map = new HashMap<>();
        map.put("nickName", "changedNickName");
        String body = new ObjectMapper().writeValueAsString(map);

        //when
        //then
        mvc.perform(patch("/api/user/my/nickname").header(header, parsedAccessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("changedNickName")))
                .andExpect(jsonPath("$.data.profileImage", is(savedUser.getProfile())))
                .andExpect(jsonPath("$.data.totalCount", is(0)));
    }
}