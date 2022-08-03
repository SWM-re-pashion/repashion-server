package rePashion.server.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.model.UserAgreement;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoDto {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class KakaoAccount{

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class Profile{
            String nickname;
            String thumbnail_image_url;
            String profile_image_url;
            boolean is_default_image;
        }

        boolean profile_nickname_needs_agreement;
        boolean profile_image_needs_agreement;
        Profile profile;
        boolean has_email;
        boolean email_needs_agreement;
        boolean is_email_valid;
        boolean is_email_verified;
        String email;
        boolean has_age_range;
        boolean age_range_needs_agreement;
        String age_range;
        boolean has_gender;
        boolean gender_needs_agreement;
        String gender;
    }

    Long id;
    String connected_at;
    KakaoAccount kakao_account;

    public String getEmail(){
        return this.getKakao_account().getEmail();
    }

    public User toUserEntity(){
        return User.builder()
                .nickName(this.getKakao_account().getProfile().getNickname())
                .email(this.getKakao_account().getEmail())
                .ageRange((this.getKakao_account().getAge_range()))
                //.userAgreement(this.toUserAgreementEntity())
                .build();
    }

    public UserAgreement toUserAgreementEntity(){
        return UserAgreement.builder()
                .is_default_image(this.getKakao_account().getProfile().is_default_image())
                .profile_nickname_needs_agreement(this.getKakao_account().isProfile_nickname_needs_agreement())
                .profile_image_needs_agreement(this.getKakao_account().isProfile_image_needs_agreement())
                .has_email(this.getKakao_account().isHas_email())
                .email_needs_agreement(this.getKakao_account().isEmail_needs_agreement())
                .is_email_valid((this.getKakao_account().is_email_valid()))
                .is_email_verified(this.getKakao_account().is_email_verified())
                .has_age_range(this.getKakao_account().isHas_age_range())
                .age_range_needs_agreement(this.getKakao_account().isAge_range_needs_agreement())
                .has_gender(this.getKakao_account().isHas_gender())
                .gender_needs_agreement(this.getKakao_account().isGender_needs_agreement())
                .build();
    }
}
