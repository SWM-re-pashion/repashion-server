package rePashion.server.domain.statics.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubCategory {

    @Id
    @GeneratedValue
    Long id;

    String name;

    String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private ParentCategory parent;

    public SubCategory(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void changeGender(ParentCategory parent){
        this.parent = parent;
        parent.getChildrens().add(this);
    }
}
