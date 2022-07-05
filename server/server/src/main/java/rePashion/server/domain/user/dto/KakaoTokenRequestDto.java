package rePashion.server.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoTokenRequestDto {
    private String grant_type;
    private String client_id;
    private String redirect_url;
    private String code;
}
