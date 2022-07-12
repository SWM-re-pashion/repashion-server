package rePashion.server.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import rePashion.server.domain.user.dto.KakaoLoginRequestDto;
import rePashion.server.domain.user.dto.KakaoLoginResponseDto;

import java.util.Objects;

@Transactional
@SpringBootTest
class KakaoLoginControllerTest {

    @Autowired
    KakaoLoginController kakaoLoginController;

    @Test
    @DisplayName("정상 로그인")
    void login() throws JsonProcessingException {
        //given
        final String authToken = "CCX-3qziaJM2yMQY549o7qweEol9W5bwa3bXe4u9lEAJiBhckcZCqXfSd0kGtkHY7oD2sAo9dVwAAAGBzC7h9w";
        KakaoLoginRequestDto dto = new KakaoLoginRequestDto("kakao", authToken);

        //when
        ResponseEntity<KakaoLoginResponseDto> response = kakaoLoginController.login(dto);

        //then
        System.out.println(response.getBody());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(KakaoLoginResponseDto.Status.success);
        Assertions.assertThat(response.getBody().getUser().getEmail()).isEqualTo("ehddn977@gmail.com");
    }

    @Test
    @DisplayName("비정상 로그인 : 인가 코드 이상")
    void login_abnormal() throws JsonProcessingException {
        //given
        final String authToken = "1";
        KakaoLoginRequestDto dto = new KakaoLoginRequestDto("kakao", authToken);

        //when
        ResponseEntity<KakaoLoginResponseDto> response = kakaoLoginController.login(dto);

        //then
        System.out.println(response.getBody());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}