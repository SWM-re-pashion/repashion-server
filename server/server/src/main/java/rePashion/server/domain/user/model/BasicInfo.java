package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicInfo {

    public enum Gender{
        MALE, FEMALE
    }

    public enum BodyShape{
        SKINNY, NORMAL, CHUBBY, FAT
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(nullable = false, length = 6)   //170175, 180185
    String height;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    BodyShape bodyShape = BodyShape.NORMAL;

    @Column(length = 4)   //XL, 2XL
    String topSize = "L";

    @Column(length = 2)   //22 ~ 37
    String bottomSize = "32";

    @Builder
    public BasicInfo(Gender gender, String height, BodyShape bodyShape, String topSize, String bottomSize) {
        this.gender = gender;
        this.height = height;
        this.bodyShape = bodyShape;
        this.topSize = topSize;
        this.bottomSize = bottomSize;
    }
}
