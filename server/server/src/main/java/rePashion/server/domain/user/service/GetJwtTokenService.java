package rePashion.server.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import rePashion.server.domain.user.dto.AwsCognitoTokenDto;

@Service
@RequiredArgsConstructor
public class GetJwtTokenService {

    private final RestTemplate restTemplate;

    @Value("${cloud.aws.cognito.redirect_url}")
    private String REDIRECT_URL;

    @Value("${cloud.aws.cognito.user_pool_id}")
    private String CLIEND_ID;

    @Value("${cloud.aws.cognito.host_url}")
    private String COGNITO_URL;

    private final String endPoint = "/oauth2/token";

    public AwsCognitoTokenDto getToken(String authCode) throws JsonProcessingException {
        HttpHeaders headers = getHttpHeaders();
        LinkedMultiValueMap<String, String> requestBody = getBody(authCode);
        HttpEntity<LinkedMultiValueMap<String, String>> form = new HttpEntity<>(requestBody, headers);
        return getStringResponseEntity(COGNITO_URL+endPoint, form);
    }

    private AwsCognitoTokenDto getStringResponseEntity(String url, HttpEntity<LinkedMultiValueMap<String, String>> form) throws JsonProcessingException {
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, form, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(exchange.getBody(), AwsCognitoTokenDto.class);
    }

    private LinkedMultiValueMap<String, String> getBody(String authCode) {
        LinkedMultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", CLIEND_ID);
        requestBody.add("redirect_uri", REDIRECT_URL);
        requestBody.add("code", authCode);
        return requestBody;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }
}
