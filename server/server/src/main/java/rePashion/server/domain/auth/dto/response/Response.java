package rePashion.server.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Response {

    @Data
    @AllArgsConstructor
    public static class Reissue{
        private String accessToken;
    }

    @Data
    @AllArgsConstructor
    public static class Login{
        private String accessToken;
        @JsonIgnore
        private String refreshToken;
        private Boolean hasPreference;

        public Login(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
