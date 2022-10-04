package rePashion.server.global.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rePashion.server.domain.user.model.User;
import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidJwtTokenException;
import rePashion.server.global.jwt.JwtProvider;
import rePashion.server.global.jwt.JwtTokenDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class RefreshTokenProvider implements JwtProvider {

    @Value("${auth.jwt.issuer}")
    private String ISSUER;

    @Value("${auth.jwt.subject}")
    private String SUBJECT;

    @Value("${auth.jwt.refresh.header}")
    private String HEADER;

    @Value("${auth.jwt.refresh.expiration-time}")
    private Long expirationTime;

    @Value("${auth.jwt.refresh.secret-key}")
    private String secretKey;

    @Override
    public String parse(User user) {
        return JWT.create()
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))       // 유효기간
                .sign(Algorithm.HMAC256(secretKey));
    }

    @Override
    public JwtTokenDto parsing(String token) {
        try{
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
            return JwtTokenDto.builder()
                    .expiredAt(verify.getExpiresAt())
                    .issuedAt(verify.getIssuedAt())
                    .build();
        }
        catch(JWTVerificationException e){
            throw new InvalidJwtTokenException(ErrorCode.INVALID_REFRESH_TOKEN);           // 그 외의 오류일때
        }
    }

    @Override
    public Long getPk(String token) {
        return null;
    }

    @Override
    public String resolve(HttpServletRequest request) {
        return request.getHeader(HEADER);
    }
}
