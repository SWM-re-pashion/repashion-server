package rePashion.server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.user.dto.*;
import rePashion.server.domain.user.exception.PreferenceNotExistedException;
import rePashion.server.domain.user.model.Color;
import rePashion.server.domain.user.model.Preference;
import rePashion.server.domain.user.model.StyleImage;
import rePashion.server.domain.user.service.PreferenceService;
import rePashion.server.global.error.exception.ErrorCode;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PreferenceController {

    private final PreferenceService getPreferenceService;

    @GetMapping("/colors")
    public ResponseEntity<GetColorsResponseDto> getColors(){
        ArrayList<Color> colorLists = getPreferenceService.getAllColorLists();
        GetColorsResponseDto dto = GetColorsResponseDto.of(colorLists);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/styles")
    public ResponseEntity<GetStylesResponseDto> getStyles(){
        List<StyleImage> styleImages = getPreferenceService.getAllStyleImages();
        GetStylesResponseDto dto = GetStylesResponseDto.of(styleImages);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("preference/save")
    public ResponseEntity<PostPreferenceResponseDto> postPreference(@RequestBody @Valid PostPreferenceRequestDto requestDto){
        Optional<Preference> savedPreference = getPreferenceService.savePreference(requestDto);
        Preference preference = savedPreference.orElseThrow(() -> new PreferenceNotExistedException(ErrorCode.DB_INSERTING_ERROR));
        PostPreferenceResponseDto responseDto = PostPreferenceResponseDto.of(preference);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
