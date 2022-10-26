package rePashion.server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.auth.dto.exception.UserNotExistedException;
import rePashion.server.domain.user.exception.UserNotFoundException;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.domain.user.resources.Request;
import rePashion.server.domain.user.resources.Response;
import rePashion.server.domain.user.service.UserService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/my")
    public ResponseEntity<GlobalResponse> getMyInfo(@AuthenticationPrincipal Long userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        Response.MyInfoResponse myInfo = userService.getMyInfo(user);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, myInfo), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse> getOtherInfo(@AuthenticationPrincipal Long userId, @PathVariable Long id){
        userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Response.MyInfoResponse myInfo = userService.getMyInfo(user);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, myInfo), HttpStatus.OK);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<GlobalResponse> updateMyNickName(@AuthenticationPrincipal Long userId, @RequestBody Request.UpdateNickNameRequest request){
        User user = userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
        Response.MyInfoResponse myInfo = userService.updateMyNickName(user, request.getNickName());
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, myInfo), HttpStatus.OK);
    }
}
