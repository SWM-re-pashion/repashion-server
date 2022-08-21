package rePashion.server.domain.preference.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorEntity {

    public enum Ctype{
        TOP, BOT
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Ctype type;

    @Enumerated(EnumType.STRING)
    Color color;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "preference_id")
    private Preference preference;

    @Builder
    public ColorEntity(Color color, Ctype ctype, Preference preference) {
        this.color = color;
        this.type = ctype;
        this.preference = preference;
    }
}
