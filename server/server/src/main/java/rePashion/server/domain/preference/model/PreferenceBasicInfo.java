package rePashion.server.domain.preference.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.statics.bodyshape.BodyShape;
import rePashion.server.domain.statics.gender.Gender;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferenceBasicInfo {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(nullable = false)
    int height;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    BodyShape bodyShape;

    String topSize;

    String bottomSize;

    String topColors;

    String bottomColors;

    @Builder
    public PreferenceBasicInfo(Gender gender, int height, BodyShape bodyShape, String topSize, String bottomSize, String topColors, String bottomColors) {
        this.gender = gender;
        this.height = height;
        this.bodyShape = bodyShape;
        this.topSize = topSize;
        this.bottomSize = bottomSize;
        this.topColors = topColors;
        this.bottomColors = bottomColors;
    }
}
