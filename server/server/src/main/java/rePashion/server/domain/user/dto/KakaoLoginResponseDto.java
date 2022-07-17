package rePashion.server.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoLoginResponseDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Info{
        private String email;
        private String profileImage;
        private String thumbnailImage;
        private String nickName;
        private String ageRange;

        public Info(String email, String profileImage, String thumbnailImage, String nickName, String ageRange) {
            this.email = email;
            this.profileImage = profileImage;
            this.thumbnailImage = thumbnailImage;
            this.nickName = nickName;
            this.ageRange = ageRange;
        }
    }
    public enum Status{
        success, error
    };
    private Info user;
    private Status status;

    public KakaoLoginResponseDto(String email, String profileImage, String thumbnailImage, String nickName, String ageRange, Status status) {
        this.user = new Info(email, profileImage, thumbnailImage, nickName, ageRange);
        this.status = status;
    }
}
