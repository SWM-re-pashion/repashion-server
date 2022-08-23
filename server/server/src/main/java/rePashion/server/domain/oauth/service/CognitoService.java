package rePashion.server.domain.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rePashion.server.domain.oauth.dto.exception.CognitoEnvException;
import rePashion.server.domain.oauth.dto.exception.CognitoGrantException;
import rePashion.server.domain.oauth.dto.response.CognitoGetTokenResponseDto;
import rePashion.server.domain.oauth.dto.response.CognitoGetUserInfoResponseDto;
import rePashion.server.domain.oauth.dto.response.OauthLoginResponseDto;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CognitoService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Value("${spring.cloud.aws.security.cognito.redirect_url}")
    private String REDIRECT_URL;

    @Value("${spring.cloud.aws.security.cognito.client_id}")
    private String CLIENT_ID;

    @Value("${spring.cloud.aws.security.cognito.domain}")
    private String COGNITO_DOMAIN;

    private String accessToken;
    private String refreshToken;

    private String authCode;

    public OauthLoginResponseDto login(String authCode) throws JsonProcessingException {
        this.authCode = authCode;
        getToken();
        getUserInfo();
        return new OauthLoginResponseDto(accessToken, refreshToken);
    }

    private void getToken() throws JsonProcessingException {
        HttpHeaders headers = getHeaders();
        LinkedMultiValueMap<String, String> requestBody = getRequestBody(authCode);
        ResponseEntity<String> response = sendRequestToCognito(headers, requestBody);
        setToken(response);
    }

    private void setToken(ResponseEntity<String> response) throws JsonProcessingException {
        CognitoGetTokenResponseDto dto = new ObjectMapper().readValue(response.getBody(), CognitoGetTokenResponseDto.class);
        this.accessToken = dto.getAccess_token();
        this.refreshToken = dto.getRefresh_token();
    }

    private void getUserInfo() throws JsonProcessingException {
        HttpHeaders headers = getHeaders(this.accessToken);
        ResponseEntity<String> response = sendRequestToCognito(headers);
        CognitoGetUserInfoResponseDto dto = new ObjectMapper().readValue(response.getBody(), CognitoGetUserInfoResponseDto.class);
        registerUser(dto);
    }

    private void registerUser(CognitoGetUserInfoResponseDto dto) {
        if(isNotRegister(dto.getEmail())) {
            User savedUser = userRepository.save(dto.toUserEntity(this.refreshToken));
            savedUser.changeUsername();
        }
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
