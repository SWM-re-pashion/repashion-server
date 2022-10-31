package rePashion.server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rePashion.server.domain.auth.dto.exception.UserNotExistedException;
import rePashion.server.domain.styleimage.service.S3UploaderService;
import rePashion.server.domain.user.exception.UserNotFoundException;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.domain.user.resources.Response;
import rePashion.server.domain.user.service.UserService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    private final S3UploaderService s3UploaderService;

    @GetMapping("/my")
    public ResponseEntity<GlobalResponse> getMyInfo(@AuthenticationPrincipal Long userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        Response.MyInfoResponse myInfo = userService.getMyInfo(true, user);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, myInfo), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse> getOtherInfo(@AuthenticationPrincipal Long userId, @PathVariable Long id){
        userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Response.MyInfoResponse myInfo = userService.getMyInfo(false, user);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, myInfo), HttpStatus.OK);
    }

    @PatchMapping(value = "/my", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<GlobalResponse> updateMyInfo(@AuthenticationPrincipal Long userId, @RequestPart String name, @RequestPart MultipartFile profileImage) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        String uploadedImagePath = s3UploaderService.upload(profileImage, "profile");
        userRepository.update(name, uploadedImagePath, user.getId());
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, user.getId()), HttpStatus.OK);
    }
}
