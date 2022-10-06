package rePashion.server.domain.statics.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Statics {

    public static enum Type{
        Style, Color, Fit, Length, Gender, Size, BodyShape, PollutionCondition, OrderDate
    }
    public static enum Classification{
        top, bottom, common
    }

    @Id @GeneratedValue
    private Long id;

    private Type type;

    private Classification classification;

    private String name;

    private String code;

    public Statics(Type type, Classification classification, String name, String code) {
        this.type = type;
        this.classification = classification;
        this.name = name;
        this.code = code;
    }
}
