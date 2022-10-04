package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthority {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public UserAuthority(Role role) {
        this.role = role;
    }

    public String getRoleName(){
        return this.role.toString();
    }

    public void changeAuthority(User user){
        this.user = user;
        user.getAuthorities().add(this);
    }
}
