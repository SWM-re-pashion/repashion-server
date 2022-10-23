package rePashion.server.domain.preference.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.preference.dto.PostPreferenceRequestDto;
import rePashion.server.domain.preference.model.Preference;
import rePashion.server.domain.preference.model.PreferenceBasicInfo;
import rePashion.server.domain.preference.repository.PreferenceRepository;
import rePashion.server.domain.user.model.User;

@Getter
@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;

    /**
     * 사용자의 선호 정보들을 DB에 저장하는 함수
     *
     * @param user
     * @param dto  Controller단에서 넘어오는 dto로 선호정보가 모두 담겨있음
     * @return Long    DB에 저장된 preference의 pk
     */
    public Long save(User user, PostPreferenceRequestDto dto){
        Preference preference = new Preference(new PreferenceBasicInfo(dto));
        preference.changeUser(user);
        return preferenceRepository.save(preference).getId();
    }
}
