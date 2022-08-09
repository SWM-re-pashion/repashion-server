package rePashion.server.domain.product.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.embedded.BasicInfo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    BasicInfo basicInfo;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
    private ProductAdvanceInfo info;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private ArrayList<ProductImage> images = new ArrayList<>();

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
