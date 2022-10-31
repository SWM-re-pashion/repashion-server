package rePashion.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserProductRepository;
import rePashion.server.domain.user.repository.UserRepository;
import rePashion.server.domain.user.resources.Response;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProductRepository userProductRepository;

    public Response.MyInfoResponse getMyInfo(boolean isMe, User user){
        int count = userProductRepository.getNumberOfPurchasingByUser(user.getId());
        String email = (isMe)? user.getEmail() : null;
        return new Response.MyInfoResponse(email, user.getNickName(), user.getProfile(), count);
    }
}
