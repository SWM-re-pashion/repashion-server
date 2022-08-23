package rePashion.server.domain.oauth.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CognitoGetUserInfoResponseDto {
    private String sub;
    private Boolean email_verified;
    private String email;

    public CognitoGetUserInfoResponseDto(String sub, Boolean email_verified, String email) {
        this.sub = sub;
        this.email_verified = email_verified;
        this.email = email;
    }
}
