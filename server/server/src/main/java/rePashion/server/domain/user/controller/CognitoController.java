package rePashion.server.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.user.dto.AwsCognitoTokenDto;
import rePashion.server.domain.user.dto.AwsCognitoUserInfoDto;
import rePashion.server.domain.user.service.GetJwtTokenService;
import rePashion.server.domain.user.service.GetUserInfoService;
import rePashion.server.domain.user.service.UserRegisterService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

import java.util.Map;

@RestController
@RequestMapping("/api/cognito")
@RequiredArgsConstructor
public class CognitoController {

    private final GetJwtTokenService getJwtTokenService;
    private final GetUserInfoService getUserInfoService;
    private final UserRegisterService userRegisterService;

    @GetMapping("/token/{code}")
    public ResponseEntity<GlobalResponse> getToken(@PathVariable("code") String authCode) throws JsonProcessingException {
        AwsCognitoTokenDto token = getJwtTokenService.getToken(authCode);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, token), HttpStatus.OK);
    }

    @GetMapping("/register/{code}")
    public ResponseEntity<GlobalResponse> getUserInfo(@PathVariable("code") String authCode) throws JsonProcessingException {
        AwsCognitoTokenDto token = getJwtTokenService.getToken(authCode);
        AwsCognitoUserInfoDto userInfo = getUserInfoService.getUserInfo(token.getAccess_token());
        userRegisterService.register(userInfo);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.CREATED), HttpStatus.CREATED);
    }
}
