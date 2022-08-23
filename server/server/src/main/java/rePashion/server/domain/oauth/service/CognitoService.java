package rePashion.server.domain.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import rePashion.server.domain.oauth.dto.response.CognitoGetTokenResponseDto;
import rePashion.server.domain.oauth.dto.response.CognitoGetUserInfoResponseDto;

@Service
@RequiredArgsConstructor
public class CognitoService {

    private final RestTemplate restTemplate;

    @Value("${spring.cloud.aws.security.cognito.redirect_url}")
    private String REDIRECT_URL;

    @Value("${spring.cloud.aws.security.cognito.client_id}")
    private String CLIENT_ID;

    @Value("${spring.cloud.aws.security.cognito.domain}")
    private String COGNITO_DOMAIN;

    public CognitoGetTokenResponseDto getToken(String authCode) throws JsonProcessingException {
        HttpHeaders headers = getHeaders();
        LinkedMultiValueMap<String, String> requestBody = getRequestBody(authCode);
        ResponseEntity<String> response = sendRequestToCognito(headers, requestBody);
        return new ObjectMapper().readValue(response.getBody(), CognitoGetTokenResponseDto.class);
    }

    public CognitoGetUserInfoResponseDto getUserInfo(){
        return null;
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

    private ResponseEntity<String> sendRequestToCognito(HttpHeaders headers, LinkedMultiValueMap<String,String> body){
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.exchange(COGNITO_DOMAIN + "/oauth2/token", HttpMethod.POST, request, String.class);
    }
}
