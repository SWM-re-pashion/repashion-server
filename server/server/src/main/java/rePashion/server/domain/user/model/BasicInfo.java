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
        남성, 여성
    }

    public enum BodyShape{
        마름, 보통, 통통, 뚱뚱
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(nullable = false, length = 3)   //170
    String height;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    BodyShape bodyShape = BodyShape.보통;

    //XL, 2XL
    String topSize = "XL";

    //22 ~ 37
    String bottomSize = "32";

    String topColors = "";

    String bottomColors = "";

    @Builder
    public BasicInfo(Gender gender, String height, BodyShape bodyShape, String topSize, String bottomSize, String topColors, String bottomColors) {
        this.gender = gender;
        this.height = height;
        this.bodyShape = bodyShape;
        this.topSize = topSize;
        this.bottomSize = bottomSize;
        this.topColors = topColors;
        this.bottomColors = bottomColors;
    }
}
