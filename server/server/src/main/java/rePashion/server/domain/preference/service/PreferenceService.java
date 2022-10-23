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

    /**
     * 사용자의 선호 정보들을 DB에 저장하는 함수
     *
     * @param   dto Controller단에서 넘어오는 dto로 선호정보가 모두 담겨있음
     * @return  Long    DB에 저장된 preference의 pk
     */
    public Optional<Preference> savePreference(PostPreferenceRequestDto dto){
        Preference preference = new Preference(dto.toBasicInfo());
        return Optional.of(preferenceRepository.save(preference));
    }
}
