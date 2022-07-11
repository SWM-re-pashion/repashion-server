package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Style {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String imageUrl;

    @Column(nullable = false)
    String imageProperties;

    @ManyToOne
    @JoinColumn(name = "preference_id")
    private Preference preference;
}
