package rePashion.server.domain.user.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Response {

    @Data
    @AllArgsConstructor
    public static class MyInfoResponse{
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String email;
        private String name;
        private String profileImage;
        private int totalCount;
    }
}
