package rePashion.server.domain.auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import rePashion.server.domain.user.model.Role;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.model.UserAuthority;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.jwt.impl.AccessTokenProvider;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthTestingControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    AccessTokenProvider accessTokenProvider;

    @Value("${auth.jwt.access.header}")
    private String header;

    @Value("${auth.jwt.access.secret-key}")
    private String secretKey;

    @Autowired
    UserRepository userRepository;

    @Test
    void unauthorized_테스팅() throws Exception {
        //given

        //when

        //then
        mvc.perform(get("/auth-verification"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 정상적인_토큰_테스팅() throws Exception {
        //given
        User user = new User("test@test.com", "test");
        UserAuthority userAuthority = new UserAuthority(Role.ROLE_USER);
        userAuthority.changeAuthority(user);
        User save = userRepository.save(user);
        String token = accessTokenProvider.parse(save);

        //when
        //then
        mvc.perform(get("/auth-verification").header(header, token))
                .andExpect(status().isOk());
    }

    @Test
    void 비정상적인_토큰_테스팅_유효기간_지남() throws Exception {
        //given
        String token = JWT.create()
                .withSubject("test")
                .withIssuer("test")
                .withIssuedAt(new Date(System.currentTimeMillis() - 1000))
                .withExpiresAt(new Date(System.currentTimeMillis() - 1000))       // 유효기간
                .withClaim("userId", 1L)                            // 이메일
                .withClaim("authority", "ROLE_USER")   // 권한
                .sign(Algorithm.HMAC256(secretKey));

        //when

        //then
        mvc.perform(get("/auth-verification").header(header, token))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", is("token has expired.")))
                .andExpect(jsonPath("$.code", is("TOKEN_EXPIRED")));
    }

    @Test
    void 저장되지_않은_유저_조회() throws Exception {
        //given
        User user = new User("test@test.com", "test");
        UserAuthority userAuthority = new UserAuthority(Role.ROLE_USER);
        userAuthority.changeAuthority(user);
        String token = accessTokenProvider.parse(user);

        //when
        //then
        mvc.perform(get("/auth-verification").header(header, token))
                .andExpect(status().is4xxClientError());
    }
}