package rePashion.server.domain.preference.model;

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

    public StyleImage(String imageUrl, String imageProperties) {
        this.imageUrl = imageUrl;
        this.imageProperties = imageProperties;
    }
}
