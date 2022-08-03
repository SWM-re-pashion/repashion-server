package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.product.model.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    private String email;

    private String ageRange;

    private enum gender{
        M,W
    };

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seller")
    private List<Product> products = new ArrayList<>();

//    @Embedded
//    private UserAgreement userAgreement;

    @Builder
    public User(Long id, String nickName, String email, String ageRange) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.ageRange = ageRange;
        //this.userAgreement = userAgreement;
    }
}
