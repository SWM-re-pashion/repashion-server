package rePashion.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.user.dto.AwsCognitoUserInfoDto;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;

    public void register(AwsCognitoUserInfoDto userInfo){
        if(!isRegister(userInfo.getEmail()))                                   // 해당 유저가 이미 회원가입이 완료된 유저인지 확인한다
            userRegister(userInfo);
    }

    private boolean isRegister(String email){
        List<User> findUser = userRepository.findUserByEmail(email);
        return !findUser.isEmpty();
    }

    private void userRegister(AwsCognitoUserInfoDto userInfo){
        User user = User.builder()
                .email(userInfo.getEmail())
                .nickName(userInfo.getNickname())
                .build();
         userRepository.save(user);
    }
}
