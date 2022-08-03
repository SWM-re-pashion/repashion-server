package rePashion.server.domain.user.dto;

import lombok.Data;

@Data
public class AwsCognitoUserInfoDto {
    private String sub;
    private String email_verified;
    private String profile;
    private String nickname;
    private String email;
    private String username;
}
