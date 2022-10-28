package rePashion.server.domain.product.model.measure.entity;

import rePashion.server.domain.product.dto.MeasureDto;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.product.model.ProductAdvanceInfo;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class  Measure {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    public abstract MeasureDto getMeasureDto();
    public void changeProduct(Product product){
        this.product = product;
    }
}
