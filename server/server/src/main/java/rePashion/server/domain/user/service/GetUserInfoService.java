package rePashion.server.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rePashion.server.domain.user.dto.AwsCognitoUserInfoDto;
import rePashion.server.domain.user.exception.CognitoException;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUserInfoService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final String endPoint = "/oauth2/userInfo";

    @Value("${cloud.aws.cognito.host_url}")
    private final String COGNITO_URL;

    public AwsCognitoUserInfoDto getUserInfo(String token) throws JsonProcessingException {
        HttpHeaders httpHeaders = getHttpHeaders(token);
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        return getStringResponseEntity(entity, COGNITO_URL + endPoint);
    }

    public Long getPkOfUserInfo(String token) throws JsonProcessingException {
        AwsCognitoUserInfoDto userInfo = getUserInfo(token);
        List<User> userByEmail = userRepository.findUserByEmail(userInfo.getEmail());
        return userByEmail.get(0).getId();
    }

    private AwsCognitoUserInfoDto getStringResponseEntity(HttpEntity<Object> entity, String url) throws JsonProcessingException {
        ResponseEntity<String> exchange = getExchange(entity, url);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(exchange.getBody(), AwsCognitoUserInfoDto.class);
    }

    private ResponseEntity<String> getExchange(HttpEntity<Object> entity, String url) {
        try{
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        }catch(HttpClientErrorException ignored){
            throw new CognitoException(ErrorCode.INVALID_TOKEN_OR_REQUEST);
        }
    }

    private HttpHeaders getHttpHeaders(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        return httpHeaders;
    }
}
