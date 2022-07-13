package rePashion.server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.user.dto.PreferenceRequestDto;
import rePashion.server.domain.user.model.Color;
import rePashion.server.domain.user.model.Preference;
import rePashion.server.domain.user.model.StyleImage;
import rePashion.server.domain.user.service.PreferenceService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/preference")
public class PreferenceController {

    private final PreferenceService getPreferenceService;

    @GetMapping("/colors")
    public ResponseEntity<?> getColors(){
        ArrayList<Color> colorLists = getPreferenceService.getAllColorLists();
        return new ResponseEntity<>(colorLists, HttpStatus.OK);
    }

    @GetMapping("/styles")
    public ResponseEntity<?> getStyles(){
        List<StyleImage> styleImages = getPreferenceService.getAllStyleImages();
        return new ResponseEntity<>(styleImages, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePreference(@RequestBody @Valid PreferenceRequestDto dto){
        Optional<Preference> savedPreference = getPreferenceService.savePreference(dto);
        savedPreference.orElseThrow(IllegalArgumentException::new);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
