package rePashion.server.domain.product.model.measure.entity;

import rePashion.server.domain.product.model.ProductAdvanceInfo;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class  Measure {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advanceInfoId")
    private ProductAdvanceInfo advanceInfo;

    public void setAdvanceInfo(ProductAdvanceInfo advanceInfo){
        this.advanceInfo = advanceInfo;
    }
}
