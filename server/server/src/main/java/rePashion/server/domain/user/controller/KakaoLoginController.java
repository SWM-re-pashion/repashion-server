package rePashion.server.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rePashion.server.domain.user.dto.KakaoLoginRequestDto;
import rePashion.server.domain.user.dto.KakaoLoginResponseDto;
import rePashion.server.domain.user.dto.KakaoUserInfoDto;
import rePashion.server.domain.user.service.KakaoSignInService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/kakao")
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoSignInService kakaoSignInService;

    @PostMapping("/login")
    public ResponseEntity<KakaoLoginResponseDto> login(@RequestBody @Valid KakaoLoginRequestDto dto) throws JsonProcessingException {
        KakaoLoginResponseDto response = kakaoSignInService.of(dto.getSocial().getToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
