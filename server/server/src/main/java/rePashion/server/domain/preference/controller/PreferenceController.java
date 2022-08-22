package rePashion.server.domain.preference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.preference.dto.PostPreferenceRequestDto;
import rePashion.server.domain.preference.exception.PreferenceNotExistedException;
import rePashion.server.domain.preference.model.Preference;
import rePashion.server.domain.preference.service.PreferenceService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;
import rePashion.server.global.error.GlobalExceptionHandler;
import rePashion.server.global.error.exception.ErrorCode;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PreferenceController {

    private final PreferenceService preferenceService;

    @PostMapping("preference")
    public ResponseEntity<GlobalResponse> postPreference(@RequestBody @Valid PostPreferenceRequestDto requestDto){
        Preference preference = preferenceService.savePreference(requestDto).orElseThrow(() -> new PreferenceNotExistedException(ErrorCode.DB_INSERTING_ERROR));
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.CREATED, preference.getPreferredStyle()), HttpStatus.CREATED);
    }
}
