package rePashion.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserProductRepository;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.domain.user.resources.Request;
import rePashion.server.domain.user.resources.Response;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProductRepository userProductRepository;
    private final UserRepository userRepository;

    public Response.MyInfoResponse getMyInfo(User user){
        int count = userProductRepository.getNumberOfPurchasingByUser(user.getId());
        return new Response.MyInfoResponse(user.getNickName(), user.getProfile(), count);
    }

    public Response.MyInfoResponse updateMyNickName(User user, String nickName){
        userRepository.updateUserNickName(nickName, user.getId());
        int count = userProductRepository.getNumberOfPurchasingByUser(user.getId());
        return new Response.MyInfoResponse(nickName, user.getProfile(), count);
    }
}
