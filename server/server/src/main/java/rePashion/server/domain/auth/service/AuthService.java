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
import rePashion.server.global.jwt.impl.AccessTokenProvider;
import rePashion.server.global.jwt.impl.RefreshTokenProvider;

import javax.servlet.http.HttpServletRequest;
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

    public TokenResponseDto login(String authCode) throws JsonProcessingException {
        CognitoGetTokenResponseDto tokens = getToken(authCode);
        User user = getUserInfo(tokens);
        return issueToken(user);
    }

    public String reissueRefreshToken(HttpServletRequest request, String refreshToken){
        String token = accessTokenProvider.resolve(request);
        Long userId = accessTokenProvider.getPk(token);
        User user = userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        compareWithSavedToken(userId, refreshToken);
        refreshTokenProvider.parsing(refreshToken);
        return accessTokenProvider.parse(user);
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

    private CognitoGetTokenResponseDto getToken(String authCode) throws JsonProcessingException {
        HttpHeaders headers = getHeaders();
        LinkedMultiValueMap<String, String> requestBody = getRequestBody(authCode);
        ResponseEntity<String> response = sendRequestToCognito(headers, requestBody);
        return new ObjectMapper().readValue(response.getBody(), CognitoGetTokenResponseDto.class);
    }

    private User getUserInfo(CognitoGetTokenResponseDto tokens) throws JsonProcessingException {
        HttpHeaders headers = getHeaders(tokens.getAccess_token());
        ResponseEntity<String> response = sendRequestToCognito(headers);
        CognitoGetUserInfoResponseDto dto = new ObjectMapper().readValue(response.getBody(), CognitoGetUserInfoResponseDto.class);
        return registerUser(dto, tokens.getRefresh_token());
    }

    private User registerUser(CognitoGetUserInfoResponseDto dto, String refreshToken) {
        if(isNotRegister(dto.getEmail())) {
            UserAuthority userAuthority = new UserAuthority(Role.ROLE_USER);
            User savedUser = userRepository.save(dto.toUserEntity(refreshToken));
            userAuthority.changeAuthority(savedUser);
            userAuthorityRepository.save(userAuthority);
            savedUser.setDefaultUserName();
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
