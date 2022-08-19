package rePashion.server.domain.statics.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DividedStaticRequestDto {
    ArrayList<StaticVarFormat> top;
    ArrayList<StaticVarFormat> bottom;

    public DividedStaticRequestDto(ArrayList<StaticVarFormat> top, ArrayList<StaticVarFormat> bottom) {
        this.top = top;
        this.bottom = bottom;
    }
}
