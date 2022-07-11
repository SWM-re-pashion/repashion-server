package rePashion.server.domain.user.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Preference {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Embedded
    BasicInfo basicInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "preference")
    private List<ColorEntity> color = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "preference")
    private List<Style> style = new ArrayList<>();

    @Builder
    public Preference(BasicInfo basicInfo, List<ColorEntity> color, List<Style> style) {
        this.basicInfo = basicInfo;
        this.color = color;
        this.style = style;
    }
}
