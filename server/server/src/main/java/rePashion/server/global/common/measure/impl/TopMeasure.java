package rePashion.server.global.common.measure.impl;

import rePashion.server.domain.product.model.Measure;
import rePashion.server.global.common.measure.MeasureRepository;
import rePashion.server.global.common.measure.MeasureType;

import java.util.ArrayList;
import java.util.HashMap;

public class TopMeasure implements MeasureRepository {

    private final String [] kindArray = new String []{"totalLength", "shoulderWidth", "chestSection", "sleeveLength"};

    @Override
    public HashMap<String, Integer> getMeasure(MeasureType type, Measure measure) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(kindArray[0], measure.getTotalLength());
        map.put(kindArray[1], measure.getShoulderWidth());
        map.put(kindArray[2], measure.getChestSection());
        map.put(kindArray[3], measure.getSleeveLength());
        return map;
    }
}
