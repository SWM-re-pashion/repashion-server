package rePashion.server.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rePashion.server.domain.auth.dto.exception.UserNotExistedException;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;


@RestController
@RequiredArgsConstructor
public class AuthTestingController {

    private final UserRepository userRepository;

    @GetMapping("/auth-verification")
    public String doUserVerification(@AuthenticationPrincipal Long userId){
        checkUser(userId);
        return "success";
    }

    private void checkUser(Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotExistedException::new);
    }
}
