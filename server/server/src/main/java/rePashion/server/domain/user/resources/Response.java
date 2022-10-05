package rePashion.server.domain.user.resources;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Response {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MyInfoResponse{
        private String name;
        private String profileImage;
        private int totalCount;
    }
}
