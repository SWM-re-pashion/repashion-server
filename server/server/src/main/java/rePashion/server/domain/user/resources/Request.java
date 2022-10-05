package rePashion.server.domain.user.resources;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Request {

    @Data
    public static class UpdateNickNameRequest{
        private String nickName;
    }
}
