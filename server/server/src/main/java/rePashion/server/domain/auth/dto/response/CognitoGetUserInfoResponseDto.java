package rePashion.server.domain.auth.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.user.model.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CognitoGetUserInfoResponseDto {
    private String sub;
    private String email_verified;
    private String email;

    private String username;

    public CognitoGetUserInfoResponseDto(String sub, String email_verified, String email, String username) {
        this.sub = sub;
        this.email_verified = email_verified;
        this.email = email;
        this.username = username;
    }

    public User toUserEntity(){
        return User.builder()
                .email(this.email)
                .nickName(this.username)
                .build();
    }
}
