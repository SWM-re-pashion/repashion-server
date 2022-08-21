package rePashion.server.domain.preference.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import rePashion.server.domain.preference.model.Color;

import java.util.ArrayList;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetColorsResponseDto {
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ColorDto{
        private String name;
        private String code;

        public ColorDto(String name, String code) {
            this.name = name;
            this.code = code;
        }
    }

    ArrayList<ColorDto> colors = new ArrayList<>();

    private GetColorsResponseDto(ArrayList<ColorDto> colors) {
        this.colors = colors;
    }

    public static GetColorsResponseDto of(ArrayList<Color> colors){
        ArrayList<ColorDto> colorDtos = new ArrayList<>();
        for(Color color : colors) {
            ColorDto colorDto = new ColorDto(color.getName(), color.getCode());
            colorDtos.add(colorDto);
        }
        return new GetColorsResponseDto(colorDtos);
    }

}
