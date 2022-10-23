package rePashion.server.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rePashion.server.domain.auth.dto.exception.*;
import rePashion.server.domain.auth.dto.response.CognitoGetUserInfoResponseDto;
import rePashion.server.domain.auth.dto.response.Response;
import rePashion.server.domain.auth.model.RefreshToken;
import rePashion.server.domain.auth.repository.RefreshTokenRepository;
import rePashion.server.domain.preference.repository.PreferenceRepository;
import rePashion.server.domain.user.model.Role;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.model.UserAuthority;
import rePashion.server.domain.user.repository.UserAuthorityRepository;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.jwt.impl.AccessTokenProvider;
import rePashion.server.global.jwt.impl.RefreshTokenProvider;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;
    private final PreferenceRepository preferenceRepository;

    @Value("${spring.cloud.aws.security.cognito.redirect_url}")
    private String REDIRECT_URL;

    @Value("${spring.cloud.aws.security.cognito.client_id}")
    private String CLIENT_ID;

    @Value("${spring.cloud.aws.security.cognito.domain}")
    private String COGNITO_DOMAIN;

    public Response.Login login(String cognitoAccessToken) throws JsonProcessingException {
        User user = getUserInfo(cognitoAccessToken);
        Response.Login response = issueToken(user);
        response.setHasPreference(hasPreferenceInfo(user));
        return response;
    }

    public String reissueRefreshToken(HttpServletRequest request, String refreshToken){
        String token = accessTokenProvider.resolve(request);
        Long userId = accessTokenProvider.getPk(token);
        User user = userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        compareWithSavedToken(userId, refreshToken);
        refreshTokenProvider.parsing(refreshToken);
        return accessTokenProvider.parse(user);
    }

    private void compareWithSavedToken(Long userId, String refreshToken){
        RefreshToken savedRefreshToken = refreshTokenRepository.findByKey(userId).orElseThrow(() -> new RefreshTokenException(ErrorCode.REFRESH_TOKEN_NOT_EXISTED));
        if(!refreshToken.equals(savedRefreshToken.getValue())) throw new RefreshTokenException(ErrorCode.REFRESH_TOKEN_NOT_MATCH);
    }

    private Response.Login issueToken(User user) {
        String accessToken = accessTokenProvider.parse(user);
        String refreshToken = refreshTokenProvider.parse(user);
        refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));
        return new Response.Login(accessToken, refreshToken);
    }

    private boolean hasPreferenceInfo(User user){
        return preferenceRepository.countPreferenceByUser(user) !=0;
    }
    private User getUserInfo(String cognitoAccessToken) throws JsonProcessingException {
        HttpHeaders headers = getCognitoHeaders(cognitoAccessToken);
        ResponseEntity<String> response = sendRequestToCognito(headers);
        CognitoGetUserInfoResponseDto dto = new ObjectMapper().readValue(response.getBody(), CognitoGetUserInfoResponseDto.class);
        return registerUser(dto);
    }

    private User registerUser(CognitoGetUserInfoResponseDto dto) {
        if(isNotRegister(dto.getEmail())) {
            UserAuthority userAuthority = new UserAuthority(Role.ROLE_USER);
            User savedUser = userRepository.save(dto.toUserEntity());
            userAuthority.changeAuthority(savedUser);
            userAuthorityRepository.save(userAuthority);
            savedUser.setDefaultUserName();
        }
        return userRepository.findUserByEmail(dto.getEmail()).orElseThrow(UserNotExistedException::new);
    }

    private boolean isNotRegister(String email) {
        return userRepository.findUserByEmail(email).isEmpty();
    }

    private HttpHeaders getCognitoHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

    private ResponseEntity<String> sendRequestToCognito(HttpHeaders headers){
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(COGNITO_DOMAIN + "/oauth2/userInfo", HttpMethod.GET, request, String.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.UNAUTHORIZED))   throw new CognitoGrantException(ErrorCode.COGNITO_TOKEN_ERROR);
            else if(e.getStatusCode().equals(HttpStatus.BAD_REQUEST))   throw new CognitoGrantException(ErrorCode.COGNITO_ENVIRONMENT_ERROR);
        }catch (Exception e){
            throw new CognitoGrantException(ErrorCode.COFNITO_SERVER_ERROR);
        }
        return null;
    }
}
