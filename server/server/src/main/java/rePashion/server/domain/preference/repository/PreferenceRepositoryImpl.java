package rePashion.server.domain.preference.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import rePashion.server.domain.preference.dto.PostPreferenceRequestDto;
import rePashion.server.domain.preference.dto.QPostPreferenceRequestDto;
import rePashion.server.domain.preference.model.Preference;
import rePashion.server.domain.preference.model.QPreference;
import rePashion.server.domain.user.model.User;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
public class PreferenceRepositoryImpl implements PreferenceCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostPreferenceRequestDto> get(User currentUser) {
        QPreference preference = QPreference.preference;

        PostPreferenceRequestDto postPreferenceRequestDto = queryFactory.select(
                        new QPostPreferenceRequestDto(
                                preference.basicInfo.gender,
                                preference.basicInfo.height,
                                preference.basicInfo.bodyShape,
                                preference.basicInfo.topSize,
                                preference.basicInfo.bottomSize,
                                preference.basicInfo.topColors,
                                preference.basicInfo.bottomColors
                        )
                )
                .from(preference)
                .where(preference.user.id.eq(currentUser.getId()))
                .fetchOne();
        return Optional.ofNullable(postPreferenceRequestDto);
    }

    @Transactional
    @Override
    public void update(Preference findPreference, PostPreferenceRequestDto dto) {
        QPreference preference = QPreference.preference;

        queryFactory.update(preference)
                .set(preference.basicInfo.gender, dto.getGender())
                .set(preference.basicInfo.height, dto.getHeight())
                .set(preference.basicInfo.bodyShape, dto.getBodyShape())
                .set(preference.basicInfo.topSize, dto.getTopSize())
                .set(preference.basicInfo.bottomSize, dto.getBottomSize())
                .set(preference.basicInfo.topColors, dto.getTopColors())
                .set(preference.basicInfo.bottomColors, dto.getBottomColors())
                .where(preference.id.eq(findPreference.getId()))
                .execute();
    }
}
