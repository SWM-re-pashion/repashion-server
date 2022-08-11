package rePashion.server.domain.product.model.measure.subclass;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.Measure;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.HashMap;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("SKIRT")
public class SkirtMeasure extends Measure {

    private Integer length;
    private Integer waistSection;
    private Integer bottomSection;

    public SkirtMeasure(HashMap<String, Integer> measure) {
        String[] kindArray = getArray();
        length = measure.get(kindArray[0]);
        waistSection = measure.get(kindArray[1]);
        bottomSection = measure.get(kindArray[2]);
    }

    @Override
    public HashMap<String, Integer> getMap() {
        String[] kindArray = getArray();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(kindArray[0], length);
        map.put(kindArray[1], waistSection);
        map.put(kindArray[2], bottomSection);
        return map;
    }

    @Override
    public String[] getArray() {
        return new String []{"length", "waistSection", "bottomSection"};
    }
}
