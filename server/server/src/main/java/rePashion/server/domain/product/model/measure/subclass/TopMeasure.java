package rePashion.server.domain.product.model.measure.subclass;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.Measure;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.HashMap;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("TOP")
public class TopMeasure extends Measure {

    private Integer length;
    private Integer shoulderWidth;
    private Integer chestSection;
    private Integer sleeveLength;

    public TopMeasure(HashMap<String, Integer> measure) {
        String[] kindArray = getArray();
        length = measure.get(kindArray[0]);
        shoulderWidth = measure.get(kindArray[1]);
        chestSection = measure.get(kindArray[2]);
        sleeveLength = measure.get(kindArray[3]);
    }

    @Override
    public HashMap<String, Integer> getMap() {
        String[] kindArray = getArray();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(kindArray[0], length);
        map.put(kindArray[1], shoulderWidth);
        map.put(kindArray[2], chestSection);
        map.put(kindArray[3], sleeveLength);
        return map;
    }

    @Override
    public String[] getArray() {
        return new String []{"length", "shoulderWidth", "chestSection", "sleeveLength"};
    }
}
