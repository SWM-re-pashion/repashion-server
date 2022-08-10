package rePashion.server.domain.product.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.embedded.BasicInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    BasicInfo basicInfo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private ProductAdvanceInfo advanceInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    @Builder
    public Product(Long id, BasicInfo basicInfo) {
        this.id = id;
        this.basicInfo = basicInfo;
    }

    public void setAdvanceInfo(ProductAdvanceInfo productAdvanceInfo){
        this.advanceInfo = productAdvanceInfo;
    }
}
