package rePashion.server.domain.preference.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import rePashion.server.domain.preference.model.StyleImage;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetStylesResponseDto {
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class StyleDto {
        private Long id;
        private String src;
        private String alt;

        public StyleDto(Long id, String src, String alt) {
            this.id = id;
            this.src = src;
            this.alt = alt;
        }
    }

    ArrayList<StyleDto> styles = new ArrayList<>();

    private GetStylesResponseDto(ArrayList<StyleDto> styles) {
        this.styles = styles;
    }

    public static GetStylesResponseDto of(List<StyleImage> styleImages) {
        ArrayList<StyleDto> styleDtos = new ArrayList<>();
        for (StyleImage image : styleImages) {
            StyleDto styleDto = new StyleDto(image.getId(), image.getImageUrl(), image.getImageProperties());
            styleDtos.add(styleDto);
        }
        return new GetStylesResponseDto(styleDtos);
    }
}

