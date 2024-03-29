package rePashion.server.domain.statics.category.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_CODE_UNIQUE", columnNames = {"NAME", "CODE"})})
public class GenderCategory {

    @Id @GeneratedValue
    Long id;

    String name;

    String code;

    @OrderBy("id asc")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gender")
    private Set<ParentCategory> childrens = new HashSet<>();

    public GenderCategory(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
