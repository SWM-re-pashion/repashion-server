package rePashion.server.domain.user.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import rePashion.server.domain.user.dto.PreferenceRequestDto;
import rePashion.server.domain.user.model.*;
import rePashion.server.domain.user.repository.PreferStyleRepository;
import rePashion.server.domain.user.repository.PreferenceRepository;
import rePashion.server.domain.user.repository.StyleImageRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PreferenceServiceTest {

    @Spy
    private PreferenceRepository preferenceRepository;

    @InjectMocks
    private PreferenceService getPreferenceService;

    @Mock
    private StyleImageRepository styleImageRepository;

    @Mock
    private PreferStyleRepository preferStyleRepository;

    @Test
    @DisplayName("Get All Colors")
    void getAllColorLists() {
        //given

        //when
        ArrayList<Color> colorLists = getPreferenceService.getAllColorLists();

        //then
        Assertions.assertThat(colorLists.size()).isEqualTo(18);
    }

    @Test
    @DisplayName("Get All Style Images")
    void getAllStyleImages() {
        //given
        StyleImage [] styleImages = new StyleImage[5];
        styleImages[0] = new StyleImage("amazons3.01.com", "");
        styleImages[1] = new StyleImage("amazons3.02.com", "");
        styleImages[2] = new StyleImage("amazons3.03.com", "");
        styleImages[3] = new StyleImage("amazons3.04.com", "");
        styleImages[4] = new StyleImage("amazons3.05.com", "");

        List<StyleImage> images = Arrays.asList(styleImages);

        //when
        when(styleImageRepository.findAll()).thenReturn(images);
        List<StyleImage> allStyleImages = getPreferenceService.getAllStyleImages();

        //then
        Assertions.assertThat(allStyleImages.size()).isEqualTo(5);
        Assertions.assertThat(allStyleImages.get(0).getImageUrl()).isEqualTo("amazons3.01.com");
    }

    @Test
    @DisplayName("enum error")
    void savePreference_enum_error_size() {
        PreferenceRequestDto dto = new PreferenceRequestDto("남성", "170", "보통", "XXXXL", "23,32", null, null);
    }

    @Test
    @DisplayName("enum error")
    void savePreference_enum_error_gender() throws IllegalArgumentException{
        PreferenceRequestDto dto = new PreferenceRequestDto("인성", "170", "보통", "XL,2XL", "23,32", null, null);
    }

//    @Test
//    @DisplayName("save Preference")
//    void savePreference() {
//        //given
//        ArrayList<Long> styles = new ArrayList<>();
//        styles.add(234L);
//        styles.add(235L);
//        styles.add(236L);
//        styles.add(237L);
//
//        ArrayList<PreferenceRequestDto.SelectedColor> selectedColors = new ArrayList<>();
//        selectedColors.add(new PreferenceRequestDto.SelectedColor("Black", "BOT"));
//        selectedColors.add(new PreferenceRequestDto.SelectedColor("White", "TOP"));
//        selectedColors.add(new PreferenceRequestDto.SelectedColor("Green", "TOP"));
//        selectedColors.add(new PreferenceRequestDto.SelectedColor("Green", "TOP"));
//        selectedColors.add(new PreferenceRequestDto.SelectedColor("Blue", "BOT"));
//
//        PreferenceRequestDto dto = new PreferenceRequestDto("남성", "170", "보통", "XL,2XL", "23,32", styles, selectedColors);
//
//        ArrayList<ColorEntity> colorEntities = new ArrayList<>();
//        for(PreferenceRequestDto.SelectedColor s : selectedColors){
//            ColorEntity colorEntity = ColorEntity.builder()
//                    .preference(null)
//                    .color(s.getColor())
//                    .ctype(s.getType())
//                    .build();
//            colorEntities.add(colorEntity);
//        }
//
//        ArrayList<PreferStyle> preferStyles = new ArrayList<>();
//        ArrayList<StyleImage> styleImages = new ArrayList<>();
//        for(Long l : dto.getStyles()){
//            StyleImage styleImage = new StyleImage(l + ".com", "");
//            styleImages.add(styleImage);
//            PreferStyle preferStyle = new PreferStyle(null, styleImage);
//            preferStyles.add(preferStyle);
//        }
//
//        Preference preference = new Preference(dto.toBasicInfo());
//        preference.choosePreferStyle(preferStyles);
//        preference.chooseColorEntity(colorEntities);
//
//        //when
//        doReturn(colorEntities.get(0)).when(colorEntityRepository).save(any());
//        doReturn(Optional.ofNullable(styleImages.get(0))).when(styleImageRepository).findById(any());
//        doReturn(preferStyles.get(0)).when(preferStyleRepository).save(any());
//        Preference savedPreference = getPreferenceService.savePreference(dto).get();
//
//        //then
//        Assertions.assertThat(savedPreference.getStyle().size()).isEqualTo(4);
//        Assertions.assertThat(savedPreference.getColor().size()).isEqualTo(5);
//    }
}