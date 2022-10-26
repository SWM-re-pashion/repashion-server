package rePashion.server.domain.preference.repository;

import rePashion.server.domain.preference.dto.PostPreferenceRequestDto;
import rePashion.server.domain.preference.model.Preference;
import rePashion.server.domain.user.model.User;

import java.util.Optional;

public interface PreferenceCustomRepository {

    Optional<PostPreferenceRequestDto> get(User currentUser);

    void update(Preference preference, PostPreferenceRequestDto dto);
}
