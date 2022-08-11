package rePashion.server.domain.product.model.embedded;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Measure {
    private int totalLength;
    private int shoulderWidth;
    private int waistSection;
    private int chestSection;
    private int thighSection;
    private int bottomSection;
    private int rise;
    private int sleeveLength;

    @Builder
    public Measure(int totalLength, int shoulderWidth, int waistSection, int chestSection, int thighSection, int bottomSection, int rise, int sleeveLength) {
        this.totalLength = totalLength;
        this.shoulderWidth = shoulderWidth;
        this.waistSection = waistSection;
        this.chestSection = chestSection;
        this.thighSection = thighSection;
        this.bottomSection = bottomSection;
        this.rise = rise;
        this.sleeveLength = sleeveLength;
    }
}
