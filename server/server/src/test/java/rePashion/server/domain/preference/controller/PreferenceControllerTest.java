package rePashion.server.domain.preference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rePashion.server.domain.preference.dto.PostPreferenceRequestDto;
import rePashion.server.domain.preference.model.Preference;
import rePashion.server.domain.preference.model.PreferenceBasicInfo;
import rePashion.server.domain.preference.repository.PreferenceRepository;
import rePashion.server.domain.preference.service.PreferenceService;
import rePashion.server.domain.user.model.Role;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.model.UserAuthority;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.jwt.impl.AccessTokenProvider;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
@SpringBootTest
@AutoConfigureMockMvc
class PreferenceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Value("${auth.jwt.access.header}")
    private String header;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccessTokenProvider accessTokenProvider;

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Test
    public void 정상적으로_preference_저장하기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        PostPreferenceRequestDto dto = new PostPreferenceRequestDto("men", 175, "chubby", "XL/2XL/3XL", "22/23/31", "Black/Green", "Black/Green");
        String body = new ObjectMapper().writeValueAsString(dto);
        //when
        //then
        mvc.perform(post("/api/preference").header(header, parsedAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data", is(1)));
    }

    @Test
    public void 정상적으로_preference_조회하기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        PostPreferenceRequestDto dto = new PostPreferenceRequestDto("men", 175, "chubby", "XL/2XL/3XL", "22/23/31", "Black/Green", "Black/Green");
        preferenceService.save(savedUser, dto);

        //when
        //then
        mvc.perform(get("/api/preference").header(header, parsedAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.gender", is("men")))
                .andExpect(jsonPath("$.data.height", is(175)))
                .andExpect(jsonPath("$.data.bodyShape", is("chubby")))
                .andExpect(jsonPath("$.data.topSize", is("XL/2XL/3XL")))
                .andExpect(jsonPath("$.data.bottomSize", is("22/23/31")))
                .andExpect(jsonPath("$.data.topColors", is("Black/Green")))
                .andExpect(jsonPath("$.data.bottomColors", is("Black/Green")))
        ;
    }

    @Test
    public void preference_저장되지_않은채로_조회하기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        //when
        //then
        mvc.perform(get("/api/preference").header(header, parsedAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("개인화 추천 입력 정보가 존재하지 않습니다")))
                .andExpect(jsonPath("$.code", is("PREFERENCE_NOT_EXISTED")))
        ;
    }

    @Test
    public void 정상적으로_preference_변경하기() throws Exception {
        //given

        // User 만들기 및 accessToken 토큰 만들기
        User user = new User("test@test.com", "hi");
        UserAuthority userAuthority1 = new UserAuthority(Role.ROLE_USER);
        userAuthority1.changeAuthority(user);
        User savedUser = userRepository.save(user);
        String parsedAccessToken = accessTokenProvider.parse(savedUser);

        PostPreferenceRequestDto dto = new PostPreferenceRequestDto("men", 175, "chubby", "XL/2XL/3XL", "22/23/31", "Black/Green", "Black/Green");
        Long savedId = preferenceService.save(savedUser, dto);

        //when
        PostPreferenceRequestDto updatedDto = new PostPreferenceRequestDto("women", 180, "fat", "S/L", "30/31/33", "Green", "Black");
        String body = new ObjectMapper().writeValueAsString(updatedDto);

        //then
        mvc.perform(patch("/api/preference").header(header, parsedAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(1)));

        Preference preference = preferenceRepository.findById(savedId).get();
        Assertions.assertThat(preference.getId()).isEqualTo(savedId);
        Assertions.assertThat(preference.getBasicInfo().getGender()).isEqualTo("women");
        Assertions.assertThat(preference.getBasicInfo().getHeight()).isEqualTo(180);
        Assertions.assertThat(preference.getBasicInfo().getBodyShape()).isEqualTo("fat");
        Assertions.assertThat(preference.getBasicInfo().getTopSize()).isEqualTo("S/L");
        Assertions.assertThat(preference.getBasicInfo().getBottomSize()).isEqualTo("30/31/33");
        Assertions.assertThat(preference.getBasicInfo().getTopColors()).isEqualTo("Green");
        Assertions.assertThat(preference.getBasicInfo().getBottomColors()).isEqualTo("Black");
    }
}