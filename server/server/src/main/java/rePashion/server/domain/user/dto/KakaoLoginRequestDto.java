package rePashion.server.domain.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoLoginRequestDto {
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Social{
        private String id;
        private String token;

        public Social(String id, String token) {
            this.id = id;
            this.token = token;
        }
    }
    private Social social;

    public KakaoLoginRequestDto(String id, String token) {
        this.social = new Social(id, token);
    }
}
