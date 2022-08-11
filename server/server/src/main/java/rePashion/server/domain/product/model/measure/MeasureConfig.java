package rePashion.server.global.common.measure;

import rePashion.server.global.common.measure.impl.BottomMeasure;
import rePashion.server.global.common.measure.impl.SkirtMeasure;
import rePashion.server.global.common.measure.impl.TopMeasure;

public class MeasureConfig {
    public static MeasureRepository determinMeasure(MeasureType type){
        if(type.equals(MeasureType.TOP)) return new TopMeasure();
        else if(type.equals(MeasureType.BOTTOM)) return new BottomMeasure();
        else return new SkirtMeasure();
    }
}
