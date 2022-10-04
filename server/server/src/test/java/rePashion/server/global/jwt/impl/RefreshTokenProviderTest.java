package rePashion.server.global.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import rePashion.server.global.error.exception.InvalidJwtTokenException;
import rePashion.server.global.jwt.JwtTokenDto;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RefreshTokenProviderTest {

    @Value("${auth.jwt.issuer}")
    private String ISSUER;

    @Value("${auth.jwt.subject}")
    private String SUBJECT;

    @Value("${auth.jwt.access.header}")
    private String HEADER;

    @Value("${auth.jwt.access.expiration-time}")
    private Long expirationTime;

    @Value("${auth.jwt.access.secret-key}")
    private String secretKey;

    @Autowired
    private RefreshTokenProvider refreshTokenProvider;

    @Test
    public void 정상적인_토큰(){
        //given
        String parsedToken = refreshTokenProvider.parse(null);

        //when
        JwtTokenDto parsing = refreshTokenProvider.parsing(parsedToken);

        //then
    }

    @Test
    public void 비정상적인_토큰_유효기간_지남(){
        //given
        String token = JWT.create()
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))       // 유효기간
                .sign(Algorithm.HMAC256(secretKey));
        //when
        InvalidJwtTokenException exception = assertThrows(InvalidJwtTokenException.class, () -> {
            refreshTokenProvider.parsing(token);
        });

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid refresh token");
    }

    @Test
    public void 비정상적인_토큰_서명_부분_오류(){
        //given
        String token = JWT.create()
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))       // 유효기간
                .sign(Algorithm.HMAC256("fake"));
        //when
        InvalidJwtTokenException exception = assertThrows(InvalidJwtTokenException.class, () -> {
            refreshTokenProvider.parsing(token);
        });

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid refresh token");
    }

    @Test
    public void 비정상_토큰_암호화_방식_오류(){
        //given
        String token = JWT.create()
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))       // 유효기간
                .sign(Algorithm.HMAC512(secretKey));
        //when
        InvalidJwtTokenException exception = assertThrows(InvalidJwtTokenException.class, () -> {
            refreshTokenProvider.parsing(token);
        });

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid refresh token");
    }
}