package rePashion.server.domain.preference.model;

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
    private List<PreferStyle> style = new ArrayList<>();

    @Builder
    public Preference(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }
    public void choosePreferStyle(List<PreferStyle> ps){
        this.style = ps;
    }
}
