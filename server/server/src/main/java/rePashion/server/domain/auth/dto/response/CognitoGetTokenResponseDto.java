package rePashion.server.domain.auth.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CognitoGetTokenResponseDto {
    private String id_token;
    private String access_token;
    private String refresh_token;
    private Long expires_in;
    private String token_type;

    public CognitoGetTokenResponseDto(String id_token, String access_token, String refresh_token, Long expires_in, String token_type) {
        this.id_token = id_token;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.token_type = token_type;
    }
}
