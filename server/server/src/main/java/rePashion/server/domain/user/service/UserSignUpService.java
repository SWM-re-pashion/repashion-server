package rePashion.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserSignUpService {

    private final UserRepository userRepository;

}
