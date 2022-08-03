package rePashion.server.domain.user.dto;

import lombok.Data;

@Data
public class AwsCognitoTokenDto {
    String id_token;
    String access_token;
    String refresh_token;
    Long expires_in;
    String token_type;
}
