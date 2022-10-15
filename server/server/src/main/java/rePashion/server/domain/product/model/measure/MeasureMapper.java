package rePashion.server.domain.product.model.measure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rePashion.server.domain.product.dto.ProductRequestBody;
import rePashion.server.domain.product.model.measure.entity.*;
import rePashion.server.domain.product.repository.MeasureRepository;

@Component
@RequiredArgsConstructor
public class MeasureMapper {

    private final MeasureRepository measureRepository;

    public Measure getMeasure(ProductRequestBody body){
        String type = body.getMeasureType();
        MeasureType measureType = MeasureType.getMeasureType(type);
        return saveProperMeasure(body.getMeasure(), measureType);
    }

    private Measure saveProperMeasure(ProductRequestBody.Measure measure, MeasureType type){
        if(type == null) return null;
        switch (type){
            case top:
                return measureRepository.save(constructTopMeasure(measure));
            case skirt:
                return measureRepository.save(constructSkirtMeasure(measure));
            case onepiece:
                return measureRepository.save(constructOnepieceMeasure(measure));
            case bottom:
                return measureRepository.save(constructBottomMeasure(measure));
        }
        return null;
    }

    private TopMeasure constructTopMeasure(ProductRequestBody.Measure measure){
        return TopMeasure
                .builder()
                .length(measure.getLength())
                .shoulderWidth(measure.getShoulderWidth())
                .chestSection(measure.getChestSection())
                .sleeveLength(measure.getSleeveLength())
                .build();
    }

    private BottomMeasure constructBottomMeasure(ProductRequestBody.Measure measure){
        return BottomMeasure
                .builder()
                .length(measure.getLength())
                .waistSection(measure.getWaistSection())
                .thighSection(measure.getThighSection())
                .rise(measure.getRise())
                .bottomSection(measure.getBottomSection())
                .build();
    }

    private SkirtMeasure constructSkirtMeasure(ProductRequestBody.Measure measure){
        return SkirtMeasure
                .builder()
                .length(measure.getLength())
                .waistSection(measure.getWaistSection())
                .bottomSection(measure.getBottomSection())
                .build();
    }

    private OnepieceMeasure constructOnepieceMeasure(ProductRequestBody.Measure measure){
        return OnepieceMeasure
                .builder()
                .length(measure.getLength())
                .shoulderWidth(measure.getShoulderWidth())
                .chestSection(measure.getChestSection())
                .sleeveLength(measure.getSleeveLength())
                .build();
    }
}
