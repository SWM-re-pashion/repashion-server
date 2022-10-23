package rePashion.server.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.auth.dto.exception.CookieNotExistedException;
import rePashion.server.domain.auth.dto.request.OauthLoginRequestDto;
import rePashion.server.domain.auth.dto.response.Response;
import rePashion.server.domain.auth.service.AuthService;
import rePashion.server.domain.preference.repository.PreferenceRepository;
import rePashion.server.domain.user.model.User;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PreferenceRepository preferenceRepository;

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> login(@RequestBody OauthLoginRequestDto dto) throws JsonProcessingException {
        Response.Login response = authService.login(dto.getAccessToken());
        ResponseCookie httpOnlyCookie = createHttpOnlyCookie(response.getRefreshToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Set-Cookie", httpOnlyCookie.toString());
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response),httpHeaders,HttpStatus.OK);
    }

    @GetMapping("/reissue")
    public ResponseEntity<GlobalResponse> reissue(@CookieValue(name = "refreshToken", defaultValue = "") String refreshToken, HttpServletRequest request){
        if(refreshToken.equals("")) throw new CookieNotExistedException();
        String accessToken = authService.reissueRefreshToken(request, refreshToken);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, new Response.Reissue(accessToken)), HttpStatus.OK);
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
