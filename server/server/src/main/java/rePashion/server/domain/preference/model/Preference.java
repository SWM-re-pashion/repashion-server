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
    PreferenceBasicInfo basicInfo;

    @OneToMany(mappedBy = "preference")
    private List<PreferStyle> style = new ArrayList<>();

    private String preferredStyle;

    @Builder
    public Preference(PreferenceBasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }
    public void choosePreferStyle(List<PreferStyle> ps){
        this.style = ps;
    }

    public void changePreferredStyle(String preferredStyle){
        this.preferredStyle = preferredStyle;
    }
}
