package rePashion.server.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rePashion.server.domain.auth.dto.exception.CognitoEnvException;
import rePashion.server.domain.auth.dto.exception.CognitoGrantException;
import rePashion.server.domain.auth.dto.exception.RefreshTokenException;
import rePashion.server.domain.auth.dto.exception.UserNotExistedException;
import rePashion.server.domain.auth.dto.response.CognitoGetTokenResponseDto;
import rePashion.server.domain.auth.dto.response.CognitoGetUserInfoResponseDto;
import rePashion.server.domain.auth.dto.response.TokenResponseDto;
import rePashion.server.domain.auth.model.RefreshToken;
import rePashion.server.domain.auth.repository.RefreshTokenRepository;
import rePashion.server.domain.user.model.Role;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.model.UserAuthority;
import rePashion.server.domain.user.repository.UserAuthorityRepository;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.jwt.JwtTokenDto;
import rePashion.server.global.jwt.impl.AccessTokenProvider;
import rePashion.server.global.jwt.impl.RefreshTokenProvider;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.cloud.aws.security.cognito.redirect_url}")
    private String REDIRECT_URL;

    @Value("${spring.cloud.aws.security.cognito.client_id}")
    private String CLIENT_ID;

    @Value("${spring.cloud.aws.security.cognito.domain}")
    private String COGNITO_DOMAIN;

    private String oAuthAccessToken;
    private String oAuthRefreshToken;

    private String authCode;

    public TokenResponseDto login(String authCode) throws JsonProcessingException {
        this.authCode = authCode;
        getToken();
        User user = getUserInfo();
        return issueToken(user);
    }

    public String reissueRefreshToken(User user, String refreshToken){
        compareWithSavedToken(user.getId(), refreshToken);
        refreshTokenProvider.parsing(refreshToken);
        return refreshTokenProvider.parse(user);
    }

    private void compareWithSavedToken(Long userId, String refreshToken){
        RefreshToken savedRefreshToken = refreshTokenRepository.findByKey(userId).orElseThrow(() -> new RefreshTokenException(ErrorCode.REFRESH_TOKEN_NOT_EXISTED));
        if(!refreshToken.equals(savedRefreshToken.getValue())) throw new RefreshTokenException(ErrorCode.REFRESH_TOKEN_NOT_MATCH);
    }

    private TokenResponseDto issueToken(User user) {
        String accessToken = accessTokenProvider.parse(user);
        String refreshToken = refreshTokenProvider.parse(user);
        refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));
        return new TokenResponseDto(accessToken, refreshToken);
    }

    private void getToken() throws JsonProcessingException {
        HttpHeaders headers = getHeaders();
        LinkedMultiValueMap<String, String> requestBody = getRequestBody(authCode);
        ResponseEntity<String> response = sendRequestToCognito(headers, requestBody);
        setToken(response);
    }

    private void setToken(ResponseEntity<String> response) throws JsonProcessingException {
        CognitoGetTokenResponseDto dto = new ObjectMapper().readValue(response.getBody(), CognitoGetTokenResponseDto.class);
        this.oAuthAccessToken = dto.getAccess_token();
        this.oAuthRefreshToken = dto.getRefresh_token();
    }

    private User getUserInfo() throws JsonProcessingException {
        HttpHeaders headers = getHeaders(this.oAuthAccessToken);
        ResponseEntity<String> response = sendRequestToCognito(headers);
        CognitoGetUserInfoResponseDto dto = new ObjectMapper().readValue(response.getBody(), CognitoGetUserInfoResponseDto.class);
        return registerUser(dto);
    }

    private User registerUser(CognitoGetUserInfoResponseDto dto) {
        if(isNotRegister(dto.getEmail())) {
            UserAuthority userAuthority = new UserAuthority(Role.ROLE_USER);
            User savedUser = userRepository.save(dto.toUserEntity(this.oAuthRefreshToken));
            userAuthority.changeAuthority(savedUser);
            userAuthorityRepository.save(userAuthority);
            savedUser.changeUsername();
        }
        return userRepository.findUserByEmail(dto.getEmail()).orElseThrow(UserNotExistedException::new);
    }

    private boolean isNotRegister(String email) {
        return userRepository.findUserByEmail(email).isEmpty();
    }

    private LinkedMultiValueMap<String, String> getRequestBody(String authCode) {
        LinkedMultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("redirect_uri", REDIRECT_URL);
        requestBody.add("code", authCode);
        return requestBody;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        return headers;
    }

    private HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

    private ResponseEntity<String> sendRequestToCognito(HttpHeaders headers){
        ResponseEntity<String> response=null;
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(COGNITO_DOMAIN + "/oauth2/userInfo", HttpMethod.GET, request, String.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
                if(Objects.equals(e.getMessage(), "400 Bad Request: \"{\"error\":\"invalid_grant\"}\"")) throw new CognitoGrantException();
                else throw new CognitoEnvException();
            }
        }
        return response;
    }

    private ResponseEntity<String> sendRequestToCognito(HttpHeaders headers, LinkedMultiValueMap<String,String> body){
        ResponseEntity<String> response=null;
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        try {
            response = restTemplate.exchange(COGNITO_DOMAIN + "/oauth2/token", HttpMethod.POST, request, String.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
                if(Objects.equals(e.getMessage(), "400 Bad Request: \"{\"error\":\"invalid_grant\"}\"")) throw new CognitoGrantException();
                else throw new CognitoEnvException();
            }
        }
        return response;
    }
}
