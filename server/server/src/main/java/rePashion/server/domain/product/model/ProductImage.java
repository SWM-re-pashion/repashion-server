package rePashion.server.domain.product.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Builder
    public ProductImage(Long id, String imagePath, Product product) {
        this.id = id;
        this.imagePath = imagePath;
        this.product = product;
    }

    public void changeProduct(Product product){
        this.product = product;
        product.getImages().add(this);
    }
}
