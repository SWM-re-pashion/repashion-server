package rePashion.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rePashion.server.domain.user.model.User;
import rePashion.server.domain.user.repository.UserProductRepositoryImpl;
import rePashion.server.domain.user.resources.Response;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProductRepositoryImpl userProductRepository;

    public Response.MyInfoResponse getMyInfo(User user){
        int count = userProductRepository.getNumberOfPurchasingByUser(user.getId());
        return new Response.MyInfoResponse(user.getNickName(), user.getProfile(), count);
    }
}
