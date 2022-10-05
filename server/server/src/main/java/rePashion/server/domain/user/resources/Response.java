package rePashion.server.domain.user.resources;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Response {

    @Data
    @AllArgsConstructor
    public static class MyInfoResponse{
        private String name;
        private String profileImage;
        private int totalCount;
    }
}
