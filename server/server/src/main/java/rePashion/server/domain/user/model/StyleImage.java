package rePashion.server.domain.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StyleImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String imageUrl;

    @Column(nullable = false)
    String imageProperties;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "styleImage")
    private List<PreferStyle> selectedPrefer = new ArrayList<>();

    public StyleImage(String imageUrl, String imageProperties) {
        this.imageUrl = imageUrl;
        this.imageProperties = imageProperties;
    }

    public void addPreference(PreferStyle selectedPrefer) {
        this.selectedPrefer.add(selectedPrefer);
    }
}
