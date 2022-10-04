package rePashion.server.domain.oauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.oauth.dto.request.OauthLoginRequestDto;
import rePashion.server.domain.oauth.dto.response.OauthLoginResponseDto;
import rePashion.server.domain.oauth.service.CognitoService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

@RestController
@RequestMapping("/api/oauth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class OauthController {

    private final CognitoService cognitoService;

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> login(@RequestBody OauthLoginRequestDto dto) throws JsonProcessingException {
        OauthLoginResponseDto tokens = cognitoService.login(dto.getAuthCode());
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, tokens), HttpStatus.OK);
    }
}
