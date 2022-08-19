package rePashion.server.domain.statics.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DividedSingleStaticRequestDto {
    ArrayList<SingleStaticVarFormat> top;
    ArrayList<SingleStaticVarFormat> bottom;

    public DividedSingleStaticRequestDto(ArrayList<SingleStaticVarFormat> top, ArrayList<SingleStaticVarFormat> bottom) {
        this.top = top;
        this.bottom = bottom;
    }
}
