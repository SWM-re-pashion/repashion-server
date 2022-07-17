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

    private String nickName;

    private String email;

    private String ageRange;

    private enum gender{
        M,W
    };

    @Embedded
    private UserAgreement userAgreement;

    @Builder
    public User(String nickName, String email, String ageRange, UserAgreement userAgreement) {
        this.nickName = nickName;
        this.email = email;
        this.ageRange = ageRange;
        this.userAgreement = userAgreement;
    }
}
