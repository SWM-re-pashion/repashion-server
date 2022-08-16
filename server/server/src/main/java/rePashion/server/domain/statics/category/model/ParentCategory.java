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
public class ParentCategory {

    @Id @GeneratedValue
    Long id;

    String name;

    String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genderId")
    private GenderCategory gender;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
    private List<SubCategory> childrens = new ArrayList<>();

    public ParentCategory(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void changeGender(GenderCategory gender){
        this.gender = gender;
        gender.getChildrens().add(this);
    }
}
