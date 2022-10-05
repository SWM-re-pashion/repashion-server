package rePashion.server.global.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Util {

    private final UserRepository userRepository;

    public Optional<User> getMe() {
        try {
            Object userId = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            return userRepository.findUserById((Long) userId);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
