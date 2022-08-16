package rePashion.server.domain.statics.category.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenderCategory {

    @Id @GeneratedValue
    Long id;

    String name;

    String code;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gender")
    private List<ParentCategory> childrens = new ArrayList<>();

    public GenderCategory(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
