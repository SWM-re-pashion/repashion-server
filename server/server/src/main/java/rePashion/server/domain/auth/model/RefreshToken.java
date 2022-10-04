package rePashion.server.domain.auth.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {

    @Id
    private Long key;
    private String value;

    public void updateValue(String token){
        this.value = token;
    }

    public RefreshToken(Long key, String value) {
        this.key = key;
        this.value = value;
    }
}
