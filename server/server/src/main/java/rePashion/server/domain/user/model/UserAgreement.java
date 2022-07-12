package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAgreement {
    boolean is_default_image;
    boolean profile_nickname_needs_agreement;
    boolean profile_image_needs_agreement;
    boolean has_email;
    boolean email_needs_agreement;
    boolean is_email_valid;
    boolean is_email_verified;
    boolean has_age_range;
    boolean age_range_needs_agreement;
    boolean has_gender;
    boolean gender_needs_agreement;

    @Builder
    public UserAgreement(boolean is_default_image, boolean profile_nickname_needs_agreement, boolean profile_image_needs_agreement, boolean has_email, boolean email_needs_agreement, boolean is_email_valid, boolean is_email_verified, boolean has_age_range, boolean age_range_needs_agreement, boolean has_gender, boolean gender_needs_agreement) {
        this.is_default_image = is_default_image;
        this.profile_nickname_needs_agreement = profile_nickname_needs_agreement;
        this.profile_image_needs_agreement = profile_image_needs_agreement;
        this.has_email = has_email;
        this.email_needs_agreement = email_needs_agreement;
        this.is_email_valid = is_email_valid;
        this.is_email_verified = is_email_verified;
        this.has_age_range = has_age_range;
        this.age_range_needs_agreement = age_range_needs_agreement;
        this.has_gender = has_gender;
        this.gender_needs_agreement = gender_needs_agreement;
    }
}