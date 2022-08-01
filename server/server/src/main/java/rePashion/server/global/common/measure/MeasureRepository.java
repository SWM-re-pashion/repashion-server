package rePashion.server.global.common.measure;


import rePashion.server.domain.product.model.Measure;

import java.util.HashMap;

public interface MeasureRepository {

    HashMap<String, Integer> getMeasure(MeasureType type, Measure measure);

    public boolean isValid(HashMap<String, Integer> map);
}
