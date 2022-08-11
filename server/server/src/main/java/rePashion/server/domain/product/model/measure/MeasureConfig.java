package rePashion.server.domain.product.model.measure;

import rePashion.server.domain.product.model.Measure;
import rePashion.server.domain.product.model.measure.subclass.BottomMeasure;
import rePashion.server.domain.product.model.measure.subclass.SkirtMeasure;
import rePashion.server.domain.product.model.measure.subclass.TopMeasure;

import java.util.HashMap;

public class MeasureConfig {
    public static Measure determinMeasure(MeasureType type, HashMap<String, Integer> measure){
        if(type.equals(MeasureType.TOP)) return new TopMeasure(measure);
        else if(type.equals(MeasureType.BOTTOM)) return new BottomMeasure(measure);
        else return new SkirtMeasure(measure);
    }
}
