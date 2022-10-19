package rePashion.server.domain.auth.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthLoginRequestDto {
    private String accessToken;

    public OauthLoginRequestDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
