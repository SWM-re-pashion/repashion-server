package rePashion.server.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import rePashion.server.domain.user.dto.PostPreferenceRequestDto;
import rePashion.server.domain.user.model.Color;
import rePashion.server.domain.user.model.Preference;
import rePashion.server.domain.user.model.StyleImage;
import rePashion.server.domain.user.service.PreferenceService;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PreferenceController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class PreferenceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PreferenceService preferenceService;

    @DisplayName("[GET] get all colors")
    @Test
    public void getAllColorLists() throws Exception {
        //given
        ArrayList<Color> colors = Color.getAll();

        //when
        when(preferenceService.getAllColorLists()).thenReturn(colors);
        ResultActions perform = mvc.perform(get("/api/colors"));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.colors.size()").value(18))
                .andExpect(jsonPath("$.colors[0:1].name").value("Black"));
    }

    @DisplayName("[GET] get all styles")
    @Test
    public void getAllStyles() throws Exception {
        //given
        List<StyleImage> images = new ArrayList<>();
        images.add(new StyleImage("amazons301.com","힙합"));
        images.add(new StyleImage("amazons302.com","힙합"));
        images.add(new StyleImage("amazons303.com","힙합"));
        images.add(new StyleImage("amazons304.com","힙합"));

        //when
        when(preferenceService.getAllStyleImages()).thenReturn(images);
        ResultActions perform = mvc.perform(get("/api/styles"));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.styles.size()").value(4))
                .andExpect(jsonPath("$.styles[0:1].src").value(images.get(0).getImageUrl()))
                .andExpect(jsonPath("$.styles[0:1].alt").value(images.get(0).getImageProperties()));
    }

    @DisplayName("[POST] post preference info")
    @Test
    public void postPreference() throws Exception {
        //given

        PostPreferenceRequestDto dto = PostPreferenceRequestDto.builder()
                .gender("남성")
                .height("175")
                .bodyShape("마름")
                .topSize("XL/2XL/3XL")
                .bottomSize("22/23/31")
                .styles(new ArrayList<Long>())
                .topColors("Black/Light_Green")
                .bottomColors("White/Silver")
                .build();
        Preference preference = new Preference(dto.toBasicInfo());
        String content = new ObjectMapper().writeValueAsString(dto);

        //when
        when(preferenceService.savePreference(dto)).thenReturn(Optional.of(preference));
        ResultActions perform = mvc.perform(post("/api/preference/save").content(content).contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.style").value("합합"));
    }
}