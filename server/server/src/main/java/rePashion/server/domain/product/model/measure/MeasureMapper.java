package rePashion.server.domain.product.model.measure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rePashion.server.domain.product.dto.MeasureDto;
import rePashion.server.domain.product.dto.ProductDto;
import rePashion.server.domain.product.model.measure.entity.*;
import rePashion.server.domain.product.repository.MeasureRepository;

@Component
@RequiredArgsConstructor
public class MeasureMapper {

    private final MeasureRepository measureRepository;

    public Measure getMeasure(ProductDto body){
        String type = body.getMeasureType();
        MeasureType measureType = MeasureType.getMeasureType(type);
        return saveProperMeasure(body.getMeasure(), measureType);
    }

    private Measure saveProperMeasure(MeasureDto measure, MeasureType type){
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

    private TopMeasure constructTopMeasure(MeasureDto measure){
        return TopMeasure
                .builder()
                .length(measure.getLength())
                .shoulderWidth(measure.getShoulderWidth())
                .chestSection(measure.getChestSection())
                .sleeveLength(measure.getSleeveLength())
                .build();
    }

    private BottomMeasure constructBottomMeasure(MeasureDto measure){
        return BottomMeasure
                .builder()
                .length(measure.getLength())
                .waistSection(measure.getWaistSection())
                .thighSection(measure.getThighSection())
                .rise(measure.getRise())
                .bottomSection(measure.getBottomSection())
                .build();
    }

    private SkirtMeasure constructSkirtMeasure(MeasureDto measure){
        return SkirtMeasure
                .builder()
                .length(measure.getLength())
                .waistSection(measure.getWaistSection())
                .bottomSection(measure.getBottomSection())
                .build();
    }

    private OnepieceMeasure constructOnepieceMeasure(MeasureDto measure){
        return OnepieceMeasure
                .builder()
                .length(measure.getLength())
                .shoulderWidth(measure.getShoulderWidth())
                .chestSection(measure.getChestSection())
                .sleeveLength(measure.getSleeveLength())
                .build();
    }
}
