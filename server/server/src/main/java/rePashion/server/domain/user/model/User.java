package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.preference.model.Preference;
import rePashion.server.domain.product.model.ProductAdvanceInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickName;

    private String profile = "https://webserver0712.s3.ap-northeast-2.amazonaws.com/profile/default.png";

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<UserAuthority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<UserProduct> products = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Preference preference;

    @Builder
    public User(String email, String nickName) {
        this.email = email;
        this.nickName = nickName;
    }

    public void setDefaultUserName(){
        this.nickName = "USER" + getId();
    }

    public String getAuthorityToString() {
        return this.authorities.stream()
                .map(UserAuthority::getRoleName)
                .collect(Collectors.joining(","));
    }

    public void changeProfileImage(String profileImage){
        this.profile = profileImage;
    }

    public void changeNickName(String nickName){this.nickName = nickName;}

    public void changePreference(Preference preference){
        this.preference = preference;
    }
}
