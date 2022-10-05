package rePashion.server.domain.product.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.embedded.BasicInfo;
import rePashion.server.domain.user.model.UserProduct;
import rePashion.server.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    BasicInfo basicInfo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private ProductAdvanceInfo advanceInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<UserProduct> users = new ArrayList<>();

    @Builder
    public Product(Long id, BasicInfo basicInfo) {
        this.id = id;
        this.basicInfo = basicInfo;
    }

    public ArrayList<String> toStringArray(){
        return this.images.stream().map(ProductImage::getImagePath).collect(Collectors.toCollection(ArrayList::new));
    }

    public void setAdvanceInfo(ProductAdvanceInfo productAdvanceInfo){
        this.advanceInfo = productAdvanceInfo;
    }
}
