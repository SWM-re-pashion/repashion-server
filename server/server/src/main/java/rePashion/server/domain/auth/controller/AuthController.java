package rePashion.server.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.auth.dto.request.OauthLoginRequestDto;
import rePashion.server.domain.auth.dto.response.TokenResponseDto;
import rePashion.server.domain.auth.service.AuthService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> login(@RequestBody OauthLoginRequestDto dto) throws JsonProcessingException {
        TokenResponseDto tokens = authService.login(dto.getAuthCode());
        HttpHeaders header = getHeader(tokens.getRefreshToken());
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, tokens), header,HttpStatus.OK);
    }

    public HttpHeaders getHeader(String refreshToken){
        ResponseCookie cookie = createHttpOnlyCookie(refreshToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Set-Header", cookie.toString());
        return httpHeaders;
    }

    public ResponseCookie createHttpOnlyCookie(String refreshToken){
        return ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(7*24*60*60)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
    }
}
