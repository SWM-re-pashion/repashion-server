package rePashion.server.domain.product.model.measure.subclass;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.Measure;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.HashMap;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("BOTTOM")
public class BottomMeasure extends Measure {

    private Integer length;
    private Integer waistSection;
    private Integer thighSection;
    private Integer rise;
    private Integer bottomSection;

    public BottomMeasure(HashMap<String, Integer> measure) {
        String[] kindArray = getArray();
        length = measure.get(kindArray[0]);
        waistSection = measure.get(kindArray[1]);
        thighSection = measure.get(kindArray[2]);
        rise = measure.get(kindArray[3]);
        bottomSection = measure.get(kindArray[4]);
    }

    @Override
    public HashMap<String, Integer> getMap() {
        String[] kindArray = getArray();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(kindArray[0], length);
        map.put(kindArray[1], waistSection);
        map.put(kindArray[2], thighSection);
        map.put(kindArray[3], rise);
        map.put(kindArray[4], bottomSection);
        return map;
    }

    @Override
    public String[] getArray() {
        return new String []{"length", "waistSection", "thighSection", "rise", "bottomSection"};
    }
}
