package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Color topColor;

    @Enumerated(EnumType.STRING)
    Color bottomColor;

    @ManyToOne
    @JoinColumn(name = "preference_id")
    private Preference preference;

    @Builder
    public ColorEntity(Color topColor, Color bottomColor, Preference preference) {
        this.topColor = topColor;
        this.bottomColor = bottomColor;
        this.preference = preference;
    }
}
