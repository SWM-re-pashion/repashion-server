package rePashion.server.domain.product.model.measure;

import rePashion.server.domain.product.model.measure.exception.MeasureException;
import rePashion.server.global.error.exception.ErrorCode;

public enum MeasureType{
    top, bottom, skirt, onepiece;

    MeasureType() {
    }

    public static MeasureType getMeasureType(String type){
        MeasureType measureType = null;
        try {
            measureType = MeasureType.valueOf(type);
        }catch (IllegalArgumentException e){
            throw new MeasureException(ErrorCode.MEASURE_DATA_ERROR);
        }
        return measureType;
    }
}
