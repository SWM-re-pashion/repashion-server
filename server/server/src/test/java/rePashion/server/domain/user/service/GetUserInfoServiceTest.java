package rePashion.server.domain.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import rePashion.server.domain.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetUserInfoServiceTest {

    @InjectMocks
    private GetUserInfoService getUserInfoService;

    @Spy
    private RestTemplate restTemplate;

    @Mock
    private UserRepository userRepository;

    @Test
    public void when_token_has_no_problem_and_user_existed(){}

    @Test
    public void when_token_has_problem(){}

    @Test
    public void when_token_has_no_problem_but_user_not_existed(){}
}