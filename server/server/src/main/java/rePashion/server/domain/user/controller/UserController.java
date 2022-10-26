package rePashion.server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.auth.dto.exception.UserNotExistedException;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.resources.Request;
import rePashion.server.domain.user.resources.Response;
import rePashion.server.domain.user.service.UserService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;
import rePashion.server.global.common.util.Util;

@RestController
@RequestMapping("/api/user/my")
@RequiredArgsConstructor
public class UserController {

    private final Util util;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<GlobalResponse> getMyInfo(){
        User user = util.getMe().orElseThrow(UserNotExistedException::new);
        Response.MyInfoResponse myInfo = userService.getMyInfo(user);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, myInfo), HttpStatus.OK);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<GlobalResponse> updateMyNickName(@RequestBody Request.UpdateNickNameRequest request){
        User user = util.getMe().orElseThrow(UserNotExistedException::new);
        Response.MyInfoResponse myInfo = userService.updateMyNickName(user, request.getNickName());
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, myInfo), HttpStatus.OK);
    }
}
