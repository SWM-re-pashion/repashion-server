package rePashion.server.global.common.measure.impl;

import rePashion.server.domain.product.model.Measure;
import rePashion.server.global.common.measure.MeasureRepository;
import rePashion.server.global.common.measure.MeasureType;

import java.util.ArrayList;
import java.util.HashMap;

public class SkirtMeasure implements MeasureRepository {

    private final String [] kindArray = new String []{"totalLength", "waistSection", "bottomSection"};

    @Override
    public HashMap<String, Integer> getMeasure(MeasureType type, Measure measure) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(kindArray[0], measure.getTotalLength());
        map.put(kindArray[1], measure.getWaistSection());
        map.put(kindArray[2], measure.getBottomSection());
        return map;
    }
}
