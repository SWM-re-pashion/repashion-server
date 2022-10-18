package rePashion.server.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.auth.dto.exception.CookieNotExistedException;
import rePashion.server.domain.auth.dto.exception.RefreshTokenException;
import rePashion.server.domain.auth.dto.exception.UserNotExistedException;
import rePashion.server.domain.auth.dto.request.OauthLoginRequestDto;
import rePashion.server.domain.auth.dto.response.TokenResponseDto;
import rePashion.server.domain.auth.service.AuthService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;
import rePashion.server.global.common.util.Util;
import rePashion.server.global.error.exception.ErrorCode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> login(@RequestBody OauthLoginRequestDto dto) throws JsonProcessingException {
        TokenResponseDto tokens = authService.login(dto.getAuthCode());
        ResponseCookie httpOnlyCookie = createHttpOnlyCookie(tokens.getRefreshToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Set-Cookie", httpOnlyCookie.toString());
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, tokens),httpHeaders,HttpStatus.OK);
    }

    @GetMapping("/reissue")
    public ResponseEntity<GlobalResponse> reissue(@CookieValue(name = "refreshToken", defaultValue = "") String refreshToken, @AuthenticationPrincipal Long userId){
        if(refreshToken.equals("")) throw new CookieNotExistedException();
        String accessToken = authService.reissueRefreshToken(userId, refreshToken);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, new TokenResponseDto(accessToken)), HttpStatus.OK);
    }

    private ResponseCookie createHttpOnlyCookie(String refreshToken){
        return ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(7*24*60*60)
                .path("/")
                // secure(true)
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .build();
    }
}
