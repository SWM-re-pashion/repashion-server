package rePashion.server.domain.preference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.auth.dto.exception.UserNotExistedException;
import rePashion.server.domain.preference.dto.PostPreferenceRequestDto;
import rePashion.server.domain.preference.service.PreferenceService;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PreferenceController {

    private final PreferenceService preferenceService;
    private final UserRepository userRepository;

    @PostMapping("/preference")
    public ResponseEntity<GlobalResponse> postPreference(@AuthenticationPrincipal Long userId, @RequestBody @Valid PostPreferenceRequestDto requestDto){
        User user = userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        Long savedId = preferenceService.save(user, requestDto);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.CREATED, savedId), HttpStatus.CREATED);
    }

    @GetMapping("/preference")
    public ResponseEntity<GlobalResponse> getPreference(@AuthenticationPrincipal Long userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        PostPreferenceRequestDto response = preferenceService.get(user);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response), HttpStatus.OK);
    }

    @PatchMapping("/preference")
    public ResponseEntity<GlobalResponse> patchPreference(@AuthenticationPrincipal Long userId, @RequestBody @Valid PostPreferenceRequestDto requestDto){
        User user = userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        Long id = preferenceService.update(user, requestDto);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, id), HttpStatus.OK);
    }
}
