package rePashion.server.domain.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.user.dto.PreferenceRequestDto;
import rePashion.server.domain.user.exception.StyleImageNotExistedException;
import rePashion.server.domain.user.model.*;
import rePashion.server.domain.user.repository.ColorEntityRepository;
import rePashion.server.domain.user.repository.PreferStyleRepository;
import rePashion.server.domain.user.repository.PreferenceRepository;
import rePashion.server.domain.user.repository.StyleImageRepository;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final StyleImageRepository styleImageRepository;
    private final PreferStyleRepository preferStyleRepository;
    private final ColorEntityRepository colorEntityRepository;

    /**
     * 모든 색깔 정보 배열을 리턴해주는 함수
     *
     * @return ArrayList Color 배열을 리턴
     */
    public ArrayList<Color> getAllColorLists(){
        return Color.getAll();
    }

    /**
     * S3에 저장된 모든 StyleImage들을 리턴해주는 함수
     *
     * @return  List  StyleImage 배열
     */
    public List<StyleImage> getAllStyleImages(){
        return styleImageRepository.findAll();
    }

    /**
     * 사용자의 선호 정보들을 DB에 저장하는 함수
     *
     * @param   dto Controller단에서 넘어오는 dto로 선호정보가 모두 담겨있음
     * @return  Long    DB에 저장된 preference의 pk
     */
    public Optional<Preference> savePreference(PreferenceRequestDto dto){
        Preference preference = createPreference(dto.toBasicInfo());
        insertColorEntities(dto, preference);
        insertPreferStyle(preference, dto.getStyles());
        return Optional.of(preferenceRepository.save(preference));
    }

    private Preference createPreference(BasicInfo basicInfo){
        return new Preference(basicInfo);
    }

    private void insertColorEntities(PreferenceRequestDto dto, Preference preference){
        ArrayList<ColorEntity> colorEntities = saveColorEntity(dto.getColors(), preference);
        preference.chooseColorEntity(colorEntities);
    }

    private ArrayList<ColorEntity> saveColorEntity(ArrayList<PreferenceRequestDto.SelectedColor> colors, Preference preference){
        ArrayList<ColorEntity> colorEntities = new ArrayList<>();
        for(PreferenceRequestDto.SelectedColor color : colors){
            ColorEntity entity = createColorEntity(preference, color);
            colorEntityRepository.save(entity);
            colorEntities.add(entity);
        }
        return colorEntities;
    }

    private ColorEntity createColorEntity(Preference preference, PreferenceRequestDto.SelectedColor color){
        return ColorEntity.builder()
                .preference(preference)
                .color(color.getColor())
                .ctype(color.getType())
                .build();
    }

    private void insertPreferStyle(Preference preference, ArrayList<Long> style){
        ArrayList<PreferStyle> preferStyle = createPreferStyles(preference, style);
        preference.choosePreferStyle(preferStyle);
    }

    private ArrayList<PreferStyle> createPreferStyles(Preference preference, ArrayList<Long> selectedStyle){
        ArrayList<PreferStyle> preferStyles = new ArrayList<>();
        for(Long s : selectedStyle){
            StyleImage styleImage = findStyleImage(s);
            PreferStyle preferStyle = savePreferStyle(preference, styleImage);
            preferStyles.add(preferStyle);
        }
        return preferStyles;
    }

    private PreferStyle savePreferStyle(Preference preference, StyleImage styleImage){
        PreferStyle preferStyle = new PreferStyle(preference, styleImage);
        return preferStyleRepository.save(preferStyle);
    }

    private StyleImage findStyleImage(Long id){
        Optional<StyleImage> foundStyleImage = styleImageRepository.findById(id);
        foundStyleImage.orElseThrow(() -> new StyleImageNotExistedException(ErrorCode.STYLE_IMAGE_NOT_EXISTED));
        return foundStyleImage.get();
    }
}
