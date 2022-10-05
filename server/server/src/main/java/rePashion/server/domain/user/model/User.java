package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    private String nickname;

    private String profile;

    private String username;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<UserAuthority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<UserProduct> products = new ArrayList<>();

    @Builder
    public User(String email, String refreshToken, String username) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.username = username;
    }

    public void changeUsername(){
        this.nickname = "USER" + getId();
    }

    public String getAuthorityToString() {
        return this.authorities.stream()
                .map(UserAuthority::getRoleName)
                .collect(Collectors.joining(","));
    }

    public void changeProfileImage(String profileImage){
        this.profile = profileImage;
    }
}
