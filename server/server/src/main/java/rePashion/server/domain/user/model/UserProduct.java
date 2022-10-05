package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.Product;
import rePashion.server.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProduct extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "purchase_status")
    PurchaseStatus purchaseStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    public UserProduct(PurchaseStatus purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public void changeUserAndProduct(User user, Product product){
        this.user = user;
        this.product = product;
        user.getProducts().add(this);
        product.getUsers().add(this);
    }
}
