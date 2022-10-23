package rePashion.server.domain.preference.model;

import lombok.*;
import rePashion.server.domain.product.model.Product;
import rePashion.server.domain.user.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Preference {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Embedded
    PreferenceBasicInfo basicInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Preference(PreferenceBasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public void changeUser(User user){
        this.user = user;
        user.changePreference(this);
    }
}
