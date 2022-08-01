package rePashion.server.global.common.measure.impl;

import rePashion.server.domain.product.model.Measure;
import rePashion.server.global.common.measure.MeasureRepository;
import rePashion.server.global.common.measure.MeasureType;

import java.util.HashMap;

public class BottomMeasure implements MeasureRepository {

    private final String [] kindArray = new String []{"totalLength", "waistSection", "thighSection", "rise", "bottomSection"};

    @Override
    public HashMap<String, Integer> getMeasure(MeasureType type, Measure measure) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(kindArray[0], measure.getTotalLength());
        map.put(kindArray[1], measure.getWaistSection());
        map.put(kindArray[2], measure.getThighSection());
        map.put(kindArray[3], measure.getRise());
        map.put(kindArray[4], measure.getBottomSection());
        return map;
    }
}
