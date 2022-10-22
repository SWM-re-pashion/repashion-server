package rePashion.server.domain.product.model.measure.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.dto.MeasureDto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DiscriminatorValue("skirt")
@Builder
@AllArgsConstructor
public class SkirtMeasure extends Measure {
    private Integer length;
    private Integer waistSection;
    private Integer bottomSection;

    @Override
    public MeasureDto getMeasureDto() {
        MeasureDto measure = new MeasureDto();
        measure.setLength(length);
        measure.setWaistSection(waistSection);
        measure.setBottomSection(bottomSection);
        return measure;
    }
}
