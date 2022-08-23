package rePashion.server.domain.oauth.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CognitoGetTokenRequestDto {
    private String grant_type;
    private String client_id;
    private String redirect_url;
    private String code;

    public CognitoGetTokenRequestDto(String grant_type, String client_id, String redirect_url, String code) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.redirect_url = redirect_url;
        this.code = code;
    }
}
