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

    @OneToMany(mappedBy = "preference")
    private List<ColorEntity> color = new ArrayList<>();

    @OneToMany(mappedBy = "preference")
    private List<PreferStyle> style = new ArrayList<>();

    @Builder
    public Preference(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }
    public void choosePreferStyle(List<PreferStyle> ps){
        this.style = ps;
    }
    public void chooseColorEntity(List<ColorEntity> colorEntities){
        this.color = colorEntities;
    }
}
