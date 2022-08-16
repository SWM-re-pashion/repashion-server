package rePashion.server.domain.statics.category;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenderCategory {

    @Id @GeneratedValue
    Long id;

    String name;

    String code;

    public GenderCategory(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
