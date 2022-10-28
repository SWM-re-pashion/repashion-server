package rePashion.server.domain.product.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.embedded.SellerNote;
import rePashion.server.domain.product.model.measure.entity.Measure;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductAdvanceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //SellerNote
    @Embedded
    SellerNote sellerNote;

    private String measureType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Builder
    public ProductAdvanceInfo(Long id, SellerNote sellerNote, String measureType) {
        this.id = id;
        this.sellerNote = sellerNote;
        this.measureType = measureType;
    }

    public void changeProduct(Product product) {
        this.product = product;
        product.setAdvanceInfo(this);
    }
}
