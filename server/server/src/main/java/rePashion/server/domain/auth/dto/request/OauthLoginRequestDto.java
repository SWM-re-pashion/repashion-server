package rePashion.server.domain.oauth.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthLoginRequestDto {
    private String authCode;

    public OauthLoginRequestDto(String authCode) {
        this.authCode = authCode;
    }
}
