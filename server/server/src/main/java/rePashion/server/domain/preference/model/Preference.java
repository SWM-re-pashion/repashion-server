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

    @Builder
    public Preference(PreferenceBasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }
}
