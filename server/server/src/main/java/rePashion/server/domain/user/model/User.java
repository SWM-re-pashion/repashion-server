package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Builder
    public User(String email, String refreshToken, String username) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.username = username;
    }

    public void changeUsername(){
        this.nickname = "USER" + getId();
    }

    public void changeProfileImage(String profileImage){
        this.profile = profileImage;
    }
}
