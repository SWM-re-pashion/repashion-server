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
public class ParentCategory {

    @Id @GeneratedValue
    Long id;

    String name;

    String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genderId")
    private GenderCategory gender;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
    private Set<SubCategory> childrens = new HashSet<>();

    public ParentCategory(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void changeGender(GenderCategory gender){
        this.gender = gender;
        gender.getChildrens().add(this);
    }
}
