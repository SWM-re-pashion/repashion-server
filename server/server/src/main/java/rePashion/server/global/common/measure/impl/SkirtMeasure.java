package rePashion.server.global.common.measure.impl;

import rePashion.server.domain.product.model.Measure;
import rePashion.server.global.common.measure.MeasureRepository;
import rePashion.server.global.common.measure.MeasureType;
import rePashion.server.global.common.measure.exception.MeasureException;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.HashMap;

public class SkirtMeasure implements MeasureRepository {

    private final String [] kindArray = new String []{"totalLength", "waistSection", "bottomSection"};

    @Override
    public HashMap<String, Integer> getMeasure(MeasureType type, Measure measure) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(kindArray[0], measure.getTotalLength());
        map.put(kindArray[1], measure.getWaistSection());
        map.put(kindArray[2], measure.getBottomSection());
        if(!isValid(map)) throw new MeasureException(ErrorCode.MEASURE_DATA_ERROR);
        return map;
    }

    @Override
    public boolean isValid(HashMap<String, Integer> map) {
        if(map.size() != kindArray.length) return false;
        for(Integer value : map.values()){
            if(value<=0) return false;
        }
        return true;
    }
}