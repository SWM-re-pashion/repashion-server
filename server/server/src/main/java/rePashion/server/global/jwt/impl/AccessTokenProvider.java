package rePashion.server.global.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import rePashion.server.domain.user.model.User;
import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidJwtTokenException;
import rePashion.server.global.jwt.JwtProvider;
import rePashion.server.global.jwt.JwtTokenDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class AccessTokenProvider implements JwtProvider {

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

    @Override
    public String parse(User user){
        return JWT.create()
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))       // 유효기간
                .withClaim("userId", user.getId())                            // 이메일
                .withClaim("authority", user.getAuthorityToString())   // 권한
                .sign(Algorithm.HMAC256(secretKey));
    }

    @Override
    public JwtTokenDto parsing(String token) {
        try{
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
            return JwtTokenDto.builder()
                    .userId(verify.getClaim("userId").asLong())
                    .authority(verify.getClaim("authority").asString())
                    .expiredAt(verify.getExpiresAt())
                    .issuedAt(verify.getIssuedAt())
                    .build();
        }
        catch(TokenExpiredException e){
            throw new InvalidJwtTokenException(ErrorCode.TOKEN_EXPIRED);        // 토큰 유효기간이 지났을때
        }
        catch(SignatureVerificationException e){
            throw new InvalidJwtTokenException(ErrorCode.INVALID_SIGNATURE_TOKEN);  // jwt signature 부분이 잘못 되었을 경우
        }
        catch(AlgorithmMismatchException e){
            throw new InvalidJwtTokenException(ErrorCode.MISMATCH_ALGORITHM);       // 암호화 방식이 잘못되었을때
        }
        catch(JWTVerificationException e){
            throw new InvalidJwtTokenException(ErrorCode.INVALID_TOKEN);           // 그 외의 오류일때
        }}

    @Override
    public String resolve(HttpServletRequest request) {
        return request.getHeader(HEADER);
    }

    @Override
    public Long getPk(String token){
        try{
            return JWT.require(Algorithm.HMAC256(secretKey))
                    .build().verify(token).getClaim("userId").asLong();
        }
        catch(TokenExpiredException ignored){
            return JWT.decode(token).getClaim("userId").asLong();
        }
        catch(SignatureVerificationException e){
            throw new InvalidJwtTokenException(ErrorCode.INVALID_SIGNATURE_TOKEN);  // jwt signature 부분이 잘못 되었을 경우
        }
        catch(AlgorithmMismatchException e){
            throw new InvalidJwtTokenException(ErrorCode.MISMATCH_ALGORITHM);       // 암호화 방식이 잘못되었을때
        }
        catch(JWTVerificationException e){
            throw new InvalidJwtTokenException(ErrorCode.INVALID_TOKEN);           // 그 외의 오류일때
        }}

    public Authentication getAuthentication(String token){

        JwtTokenDto parsedToken = this.parsing(token);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(parsedToken.getAuthority().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(parsedToken.getUserId(),"",authorities);
    }
}
