package rePashion.server.domain.auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.servlet.MockMvc;
import rePashion.server.domain.auth.model.RefreshToken;
import rePashion.server.domain.auth.repository.RefreshTokenRepository;
import rePashion.server.domain.auth.service.AuthService;
import rePashion.server.domain.user.model.Role;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.model.UserAuthority;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.jwt.impl.AccessTokenProvider;
import rePashion.server.global.jwt.impl.RefreshTokenProvider;

import javax.servlet.http.Cookie;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class RefreshReIssueTest {

    @Autowired
    private MockMvc mvc;

    @Value("${auth.jwt.access.header}")
    private String header;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenProvider refreshTokenProvider;

    @Autowired
    private AccessTokenProvider accessTokenProvider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    public void 정상적으로_재발급_받기() throws Exception {
        //given
        User user = new User("test@test.com", "", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);

        String parsedRefreshToken = refreshTokenProvider.parse(savedUser);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        Cookie requestCookie = new Cookie("refreshToken", parsedRefreshToken);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(new RefreshToken(savedUser.getId(), parsedRefreshToken));

        //when
        //then
        mvc.perform(get("/api/auth/reissue").cookie(requestCookie).header(header, parsedAccessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(parsedRefreshToken)));
    }

    @Test
    public void DB에_저장된_것과_다른_경우() throws Exception {
        //given
        User user = new User("test@test.com", "", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);

        String parsedRefreshToken = refreshTokenProvider.parse(savedUser);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        Cookie requestCookie = new Cookie("refreshToken", parsedRefreshToken);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(new RefreshToken(savedUser.getId(), parsedRefreshToken+"dfsfed"));

        //when
        //then
        mvc.perform(get("/api/auth/reissue").cookie(requestCookie).header(header, parsedAccessToken))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", is("refresh token is not matched")))
                .andExpect(jsonPath("$.code", is("REFRESH_TOKEN_NOT_MATCH")));
    }

    @Test
    public void Token이_잘못된_경우() throws Exception {
        //given
        User user = new User("test@test.com", "", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);

        String fakeRefreshToken = JWT.create()
                .withSubject("test")
                .withIssuer("test")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 10000))       // 유효기간
                .sign(Algorithm.HMAC256("fake-test"));

        String parsedAccessToken = accessTokenProvider.parse(savedUser);
        String parsedRefreshToken = refreshTokenProvider.parse(savedUser);

        Cookie requestCookie = new Cookie("refreshToken", fakeRefreshToken);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(new RefreshToken(savedUser.getId(), fakeRefreshToken));

        //when
        //then
        mvc.perform(get("/api/auth/reissue").cookie(requestCookie).header(header, parsedAccessToken))
                .andExpect(status().is(403))
                .andExpect(jsonPath("$.message", is("Invalid refresh token")))
                .andExpect(jsonPath("$.code", is("INVALID_REFRESH_TOKEN")));
    }
}