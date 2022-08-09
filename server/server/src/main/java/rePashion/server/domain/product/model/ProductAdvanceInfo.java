package rePashion.server.domain.product.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.embedded.Measure;
import rePashion.server.domain.product.model.embedded.SellerNote;

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

    //Measure
    @Embedded
    Measure measure;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Builder
    public ProductAdvanceInfo(Long id, SellerNote sellerNote, Measure measure) {
        this.id = id;
        this.sellerNote = sellerNote;
        this.measure = measure;
    }

    public void setProduct(Product product){
        this.product = product;
    }
}
