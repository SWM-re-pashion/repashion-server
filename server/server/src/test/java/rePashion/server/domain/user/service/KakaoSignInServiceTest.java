package rePashion.server.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import rePashion.server.domain.user.dto.KakaoUserInfoDto;
import rePashion.server.domain.user.model.User;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class KakaoSignInServiceTest {

    @Spy
    private RestTemplate restTemplate;

    @InjectMocks
    private KakaoSignInService kakaoSignInService;

    @Test
    @DisplayName("kakao 소셜 로그인이 제대로 되는지 확인하기")
    void getUserInfoByKakaoToken() throws IOException {

        //given
        final String KAKAO_TOKEN = "Bearer U_SumVviySodny6T6iqLlJ6TOFnbZZXKGqiZ0yl1Cj102gAAAYHHduXG";
        User mockUser = User.builder()
                .nickName("이동우")
                .ageRange("20~29")
                .email("ehddn977@gmail.com")
                .build();

        //when
        KakaoUserInfoDto userInfo = kakaoSignInService.getUserInfo(KAKAO_TOKEN);

        //then
        Assertions.assertThat(userInfo.getEmail()).isEqualTo(mockUser.getEmail());
    }

    @Test
    void getTokenByAuthCode() throws JsonProcessingException {

        //given
        final String AUTH_CODE = "A6K9SCpg2r-1BsCFJgonYfOU_13wgHEPZ1NgXm_zEz2mLEOhMrOm8mv2AIuzAueYkEjabwo9dRsAAAGByfElhA";

        //when
        kakaoSignInService.getToken(AUTH_CODE);

        //then
    }
}