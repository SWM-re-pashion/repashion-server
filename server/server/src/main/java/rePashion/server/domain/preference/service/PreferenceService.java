package rePashion.server.domain.preference.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.preference.dto.PostPreferenceRequestDto;
import rePashion.server.domain.preference.exception.StyleImageNotExistedException;
import rePashion.server.domain.preference.model.PreferStyle;
import rePashion.server.domain.preference.model.Preference;
import rePashion.server.domain.preference.model.StyleImage;
import rePashion.server.domain.preference.repository.PreferStyleRepository;
import rePashion.server.domain.preference.repository.PreferenceRepository;
import rePashion.server.domain.styleimage.repository.StyleImageRepository;
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

    /**
     * 사용자의 선호 정보들을 DB에 저장하는 함수
     *
     * @param   dto Controller단에서 넘어오는 dto로 선호정보가 모두 담겨있음
     * @return  Long    DB에 저장된 preference의 pk
     */
    public Optional<Preference> savePreference(PostPreferenceRequestDto dto){
        Preference preference = new Preference(dto.toBasicInfo());
        insertPreferStyle(preference, dto.getStyles());
        preference.changePreferredStyle(getPreferredStyle());
        return Optional.of(preferenceRepository.save(preference));
    }

    private String getPreferredStyle() {
        return "힙합";
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
