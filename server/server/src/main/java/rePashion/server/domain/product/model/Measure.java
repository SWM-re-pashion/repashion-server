package rePashion.server.domain.product.model;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Measure {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advanceInfoId")
    private ProductAdvanceInfo advanceInfo;

    public abstract HashMap<String, Integer> getMap();

    protected abstract String[] getArray();

    public void setAdvanceInfo(ProductAdvanceInfo advanceInfo){
        this.advanceInfo = advanceInfo;
    }
}
