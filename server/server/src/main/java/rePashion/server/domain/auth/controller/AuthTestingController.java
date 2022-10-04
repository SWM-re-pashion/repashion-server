package rePashion.server.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthTestingController {
    @GetMapping("/auth-testing")
    public String login(){
        return "success";
    }
}
