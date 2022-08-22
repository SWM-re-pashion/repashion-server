package rePashion.server.domain.styleimage.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rePashion.server.domain.preference.model.StyleImage;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StyleImageResponseDto {
    @Getter
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

    private StyleImageResponseDto(ArrayList<StyleDto> styles) {
        this.styles = styles;
    }

    public static StyleImageResponseDto fromEntity(List<StyleImage> styleImages) {
        ArrayList<StyleDto> dto = new ArrayList<>();
        styleImages.forEach((e) -> dto.add(new StyleDto(e.getId(), e.getImageUrl(), e.getImageProperties())));
        return new StyleImageResponseDto(dto);
    }
}
