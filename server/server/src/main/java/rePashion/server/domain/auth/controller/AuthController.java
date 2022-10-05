package rePashion.server.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.auth.dto.exception.RefreshTokenException;
import rePashion.server.domain.auth.dto.exception.UserNotExistedException;
import rePashion.server.domain.auth.dto.request.OauthLoginRequestDto;
import rePashion.server.domain.auth.dto.response.TokenResponseDto;
import rePashion.server.domain.auth.service.AuthService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;
import rePashion.server.global.common.util.Util;
import rePashion.server.global.error.exception.ErrorCode;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final Util util;

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> login(@RequestBody OauthLoginRequestDto dto) throws JsonProcessingException {
        TokenResponseDto tokens = authService.login(dto.getAuthCode());
        HttpHeaders header = getHeader(tokens.getRefreshToken());
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, tokens), header,HttpStatus.OK);
    }

    @GetMapping("/reissue")
    public ResponseEntity<GlobalResponse> reissue(@CookieValue(value = "refreshToken") String refreshToken){
        String token = authService.reissueRefreshToken(util.getMe().orElseThrow(UserNotExistedException::new), refreshToken);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, token), HttpStatus.OK);
    }

    private HttpHeaders getHeader(String refreshToken){
        ResponseCookie cookie = createHttpOnlyCookie(refreshToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Set-Header", cookie.toString());
        return httpHeaders;
    }

    private ResponseCookie createHttpOnlyCookie(String refreshToken){
        return ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(7*24*60*60)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
    }
}
