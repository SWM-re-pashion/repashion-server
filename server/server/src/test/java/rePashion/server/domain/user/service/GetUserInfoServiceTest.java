package rePashion.server.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import rePashion.server.domain.user.dto.AwsCognitoUserInfoDto;
import rePashion.server.domain.user.exception.CognitoException;
import rePashion.server.domain.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserInfoServiceTest {

    @InjectMocks
    private GetUserInfoService getUserInfoService;

    @Spy
    private RestTemplate restTemplate;

    @Mock
    private UserRepository userRepository;

    @Test
    public void when_token_has_no_problem_and_user_existed() throws JsonProcessingException {
        //given
        String token = "eyJraWQiOiJvR1dQYU5jSlNuMG9hTmFYZmxTaVB3NXBuaDByYndaeERTM1RWTXFZOVdvPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJjY2I1YWVmNi0zZDE4LTQ0NzMtYmMxZi0zNWQ0NzdiNTIxNjciLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuYXAtbm9ydGhlYXN0LTIuYW1hem9uYXdzLmNvbVwvYXAtbm9ydGhlYXN0LTJfVWY4THNTODdDIiwidmVyc2lvbiI6MiwiY2xpZW50X2lkIjoiNzNmY2FuOHBwNjd1aWpwbWU5Zmlnc2hvZGQiLCJvcmlnaW5fanRpIjoiYmMyN2MxMjYtNTYxNS00NTUyLWE1NjctM2JmMmRmNzlmNDJiIiwiZXZlbnRfaWQiOiIwM2QwZDJiZC02MWZmLTRkZmYtOTU3Mi1hM2M0MDBlMDUyM2UiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6Im9wZW5pZCIsImF1dGhfdGltZSI6MTY1OTUxMjkxMCwiZXhwIjoxNjU5NTE2NTEwLCJpYXQiOjE2NTk1MTI5MTAsImp0aSI6IjhiNjE1N2RkLWI5OTgtNDA3Ni05MmNiLTA2OWUwOGQxMzhjMSIsInVzZXJuYW1lIjoidGVzdCJ9.m0DXQ_hzYwCVayLNEKUPWR-J6-2IGXBKtJxuG2nFsZmTu1bWXpBG2ZD2CnMHemE_Zbq8tV3IwukQM2lJOrJp98O-lkcOj2r_kI1UkoAU0uWhPHvJRLNx6z0DT9K7XbcZ2AMLDAXsktibJ7Qd0nFjRV1NOGrO7dIRsXJ5AA4fjEfk6gom8_zn4BptEpYi4TuHbxvtFlDVnBN5ASP8tuYkOpSrOvpRfVhDed3VDoiIpwDbb1OlHL5xF8-E31Oc9u8OJpTuj2CRlpZqsU6ALN_pZRkuSBrBss_5ptoPXrP5MgtUVlJPoj3M7k_BCsv3RFuRqKe35htsAn0foqaxh5A1YA";

        //when
        ReflectionTestUtils.setField(getUserInfoService, "COGNITO_URL", "https://refashion.auth.ap-northeast-2.amazoncognito.com");
        AwsCognitoUserInfoDto userInfo = getUserInfoService.getUserInfo(token);

        //then
        Assertions.assertThat(userInfo.getEmail()).isEqualTo("ehddn977@naver.com");
    }

    @Test
    public void when_token_has_problem() throws JsonProcessingException {
        //given
        String token = "eyJraWQiOiJvR1dQYU5jSlNuMG9hTmFYZmxTaVB3NXBuaDByYERTM1RWTXFZOVdvPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJjY2I1YWVmNi0zZDE4LTQ0NzMtYmMxZi0zNWQ0NzdiNTIxNjciLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuYXAtbm9ydGhlYXN0LTIuYW1hem9uYXdzLmNvbVwvYXAtbm9ydGhlYXN0LTJfVWY4THNTODdDIiwidmVyc2lvbiI6MiwiY2xpZW50X2lkIjoiNzNmY2FuOHBwNjd1aWpwbWU5Zmlnc2hvZGQiLCJvcmlnaW5fanRpIjoiYmMyN2MxMjYtNTYxNS00NTUyLWE1NjctM2JmMmRmNzlmNDJiIiwiZXZlbnRfaWQiOiIwM2QwZDJiZC02MWZmLTRkZmYtOTU3Mi1hM2M0MDBlMDUyM2UiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6Im9wZW5pZCIsImF1dGhfdGltZSI6MTY1OTUxMjkxMCwiZXhwIjoxNjU5NTE2NTEwLCJpYXQiOjE2NTk1MTI5MTAsImp0aSI6IjhiNjE1N2RkLWI5OTgtNDA3Ni05MmNiLTA2OWUwOGQxMzhjMSIsInVzZXJuYW1lIjoidGVzdCJ9.m0DXQ_hzYwCVayLNEKUPWR-J6-2IGXBKtJxuG2nFsZmTu1bWXpBG2ZD2CnMHemE_Zbq8tV3IwukQM2lJOrJp98O-lkcOj2r_kI1UkoAU0uWhPHvJRLNx6z0DT9K7XbcZ2AMLDAXsktibJ7Qd0nFjRV1NOGrO7dIRsXJ5AA4fjEfk6gom8_zn4BptEpYi4TuHbxvtFlDVnBN5ASP8tuYkOpSrOvpRfVhDed3VDoiIpwDbb1OlHL5xF8-E31Oc9u8OJpTuj2CRlpZqsU6ALN_pZRkuSBrBss_5ptoPXrP5MgtUVlJPoj3M7k_BCsv3RFuRqKe35htsAn0foqaxh5A1YA";

        //when
        ReflectionTestUtils.setField(getUserInfoService, "COGNITO_URL", "https://refashion.auth.ap-northeast-2.amazoncognito.com");

        //then
        Assertions.assertThatThrownBy(() -> {
            AwsCognitoUserInfoDto userInfo = getUserInfoService.getUserInfo(token);
        }).isInstanceOf(CognitoException.class).hasMessage("코그니토 토큰 혹은 요청에 문제가 있습니다");
    }

    @Test
    public void when_request_has_problem() throws JsonProcessingException {
        //given
        String token = "eyJraWQiOiJvR1dQYU5jSlNuMG9hTmFYZmxTaVB3NXBuaDByYndaeERTM1RWTXFZOVdvPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJjY2I1YWVmNi0zZDE4LTQ0NzMtYmMxZi0zNWQ0NzdiNTIxNjciLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuYXAtbm9ydGhlYXN0LTIuYW1hem9uYXdzLmNvbVwvYXAtbm9ydGhlYXN0LTJfVWY4THNTODdDIiwidmVyc2lvbiI6MiwiY2xpZW50X2lkIjoiNzNmY2FuOHBwNjd1aWpwbWU5Zmlnc2hvZGQiLCJvcmlnaW5fanRpIjoiYmMyN2MxMjYtNTYxNS00NTUyLWE1NjctM2JmMmRmNzlmNDJiIiwiZXZlbnRfaWQiOiIwM2QwZDJiZC02MWZmLTRkZmYtOTU3Mi1hM2M0MDBlMDUyM2UiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6Im9wZW5pZCIsImF1dGhfdGltZSI6MTY1OTUxMjkxMCwiZXhwIjoxNjU5NTE2NTEwLCJpYXQiOjE2NTk1MTI5MTAsImp0aSI6IjhiNjE1N2RkLWI5OTgtNDA3Ni05MmNiLTA2OWUwOGQxMzhjMSIsInVzZXJuYW1lIjoidGVzdCJ9.m0DXQ_hzYwCVayLNEKUPWR-J6-2IGXBKtJxuG2nFsZmTu1bWXpBG2ZD2CnMHemE_Zbq8tV3IwukQM2lJOrJp98O-lkcOj2r_kI1UkoAU0uWhPHvJRLNx6z0DT9K7XbcZ2AMLDAXsktibJ7Qd0nFjRV1NOGrO7dIRsXJ5AA4fjEfk6gom8_zn4BptEpYi4TuHbxvtFlDVnBN5ASP8tuYkOpSrOvpRfVhDed3VDoiIpwDbb1OlHL5xF8-E31Oc9u8OJpTuj2CRlpZqsU6ALN_pZRkuSBrBss_5ptoPXrP5MgtUVlJPoj3M7k_BCsv3RFuRqKe35htsAn0foqaxh5A1YA";

        //when
        ReflectionTestUtils.setField(getUserInfoService, "COGNITO_URL", "https://refashion.auth.ap-northeast-2.amazoncognito");

        //then
        Assertions.assertThatThrownBy(() -> {
            AwsCognitoUserInfoDto userInfo = getUserInfoService.getUserInfo(token);
        }).isInstanceOf(CognitoException.class).hasMessage("코그니토 서버에 문제가 있습니다");
    }


    @Test
    public void when_token_has_no_problem_but_user_not_existed(){}
}