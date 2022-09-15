package rePashion.server.domain.statics.category.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OrderBy("categoryId asc")
    @OneToMany(mappedBy = "parentCategory")
    private Set<Category> children = new HashSet<>();

    private String name;

    private String code;

    private int depth;

    @Builder
    public Category(Long categoryId, String name, String code) {
        this.categoryId = categoryId;
        this.name = name;
        this.code = code;
    }

    public void updateParent(Category parent) {
        if(parent == null){
            this.parentCategory = null;
            this.depth =1;
            return;
        }
        this.parentCategory = parent;
        this.depth = parent.getDepth()+1;
        parent.getChildren().add(this);
    }
}
