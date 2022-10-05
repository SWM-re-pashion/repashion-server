package rePashion.server.global.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import rePashion.server.domain.user.model.Role;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.model.UserAuthority;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.error.exception.InvalidJwtTokenException;
import rePashion.server.global.jwt.JwtTokenDto;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccessTokenProviderTest {

    @Autowired
    private AccessTokenProvider accessTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Value("${auth.jwt.issuer}")
    private String ISSUER;

    @Value("${auth.jwt.subject}")
    private String SUBJECT;

    @Value("${auth.jwt.access.expiration-time}")
    private Long expirationTime;

    @Value("${auth.jwt.access.secret-key}")
    private String secretKey;

    @Test
    public void 정상적인_토큰(){

        //given
        User user = new User("test@test.com", "", "hi");
        UserAuthority userAuthority = new UserAuthority(Role.ROLE_USER);
        userAuthority.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedToken = accessTokenProvider.parse(savedUser);

        //when
        JwtTokenDto parsing = accessTokenProvider.parsing(parsedToken);
        Long id = userRepository.findUserById(savedUser.getId()).get().getId();

        //then
        Assertions.assertThat(parsing.getUserId()).isEqualTo(id);
        Assertions.assertThat(parsing.getAuthority()).isEqualTo("ROLE_USER");
    }

    @Test
    public void 정상적인_토큰_여러_권한을_가진(){

        //given
        User user = new User("test@test.com", "", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        UserAuthority userAuthority2 = new UserAuthority(Role.ROLE_ADMIN);
        userAuthority1.changeAuthority(user);
        userAuthority2.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedToken = accessTokenProvider.parse(savedUser);

        //when
        JwtTokenDto parsing = accessTokenProvider.parsing(parsedToken);
        Long id = userRepository.findUserById(savedUser.getId()).get().getId();

        //then
        Assertions.assertThat(parsing.getUserId()).isEqualTo(id);
        Assertions.assertThat(parsing.getAuthority()).isEqualTo(savedUser.getAuthorityToString());
    }

    @Test
    public void 정상적인_토큰_pk값_가져오기(){

        //given
        User user = new User("test@test.com", "", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedToken = accessTokenProvider.parse(savedUser);

        //when
        Long pk = accessTokenProvider.getPk(parsedToken);

        //then
        Assertions.assertThat(savedUser.getId()).isEqualTo(pk);
    }

    @Test
    public void 비정상_토큰_유효기간_지남(){
        //given
        String token = JWT.create()
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()-1000))
                .withExpiresAt(new Date(System.currentTimeMillis()-1000))       // 유효기간
                .sign(Algorithm.HMAC256(secretKey));

        //when
        InvalidJwtTokenException exception = assertThrows(InvalidJwtTokenException.class, () -> {
            accessTokenProvider.parsing(token);
        });

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("token has expired.");
    }

    @Test
    public void 비정상_토큰_서명_부분_오류(){
        //given
        String token = JWT.create()
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))       // 유효기간
                .sign(Algorithm.HMAC256("fake secret"));

        //when
        InvalidJwtTokenException exception = assertThrows(InvalidJwtTokenException.class, () -> {
            accessTokenProvider.parsing(token);
        });

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("The token's signature information is incorrect.");
    }

    @Test
    public void 비정상_토큰_암호화_방식_오류(){
        //given
        String token = JWT.create()
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))       // 유효기간
                .sign(Algorithm.HMAC512(secretKey));

        //when
        InvalidJwtTokenException exception = assertThrows(InvalidJwtTokenException.class, () -> {
            accessTokenProvider.parsing(token);
        });

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("The token encryption algorithm is incorrect.");
    }
}