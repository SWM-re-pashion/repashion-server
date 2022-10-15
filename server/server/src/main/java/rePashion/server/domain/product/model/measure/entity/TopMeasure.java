package rePashion.server.domain.product.model.measure.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DiscriminatorValue("top")
@Builder
@AllArgsConstructor
public class TopMeasure extends Measure {
    private Integer length;
    private Integer shoulderWidth;
    private Integer chestSection;
    private Integer sleeveLength;
}
