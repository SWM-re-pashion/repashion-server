package rePashion.server.domain.user.service;

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
import rePashion.server.domain.user.dto.KakaoLoginResponseDto;
import rePashion.server.domain.user.dto.KakaoTokenDto;
import rePashion.server.domain.user.dto.KakaoUserInfoDto;
import rePashion.server.domain.user.exception.KakaoLoginException;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoSignInService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Value("${kakao.login.redirect_url}")
    private String REDIRECT_URL;

    @Value("${kakao.login.restapi.key}")
    private String RESTAPI_KEY;

    public KakaoLoginResponseDto of(String authToken) throws JsonProcessingException {
        String accessToken = getToken(authToken).getAccess_token();             // 클라이언트로부터 받은 인가코드로 카카오 서버로부터 토큰 발급을 요청
        KakaoUserInfoDto userInfo = getUserInfo("Bearer " + accessToken);     // 토큰을 가지고 카카오 서버로부터 사용자 정보 가져오기 api를 호출한다
        if(!isRegister(userInfo.getEmail())) {                                  // 해당 유저가 이미 회원가입이 완료된 유저인지 확인한다
            String userEmail = userRegister(userInfo);
            assert userEmail.equals(userInfo.getEmail()) : new KakaoLoginException(ErrorCode.USER_SIGNUP_ERROR);   // 회원 가입 시에 오류가 발생한 것
        }
        return new KakaoLoginResponseDto(userInfo.getEmail(), userInfo.getKakao_account().getProfile().getProfile_image_url(), userInfo.getKakao_account().getProfile().getThumbnail_image_url(), userInfo.getKakao_account().getProfile().getNickname(), userInfo.getKakao_account().getAge_range(), KakaoLoginResponseDto.Status.success);
    }

    // 인가 코드를 통해서 카카오 서버로투터 token을 가져오는 함수
    public KakaoTokenDto getToken(String authCode) throws JsonProcessingException {
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        LinkedMultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", RESTAPI_KEY);
        requestBody.add("redirect_url", REDIRECT_URL);
        requestBody.add("code", authCode);

        HttpEntity<LinkedMultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, kakaoTokenRequest, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(exchange.getBody(), KakaoTokenDto.class);
    }

    // kako 서버로부터 user에 대한 정보를 가져오는 함수
    public KakaoUserInfoDto getUserInfo(String token) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", token);
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        String url = "https://kapi.kakao.com/v2/user/me?secure_resource=false";
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(exchange.getBody(), KakaoUserInfoDto.class);
    }

    private boolean isRegister(String email){
        List<User> findUser = userRepository.findUserByEmail(email);
        return !findUser.isEmpty();
    }

    private String userRegister(KakaoUserInfoDto dto){
        User user = dto.toUserEntity();
        return userRepository.save(user).getEmail();
    }
}
