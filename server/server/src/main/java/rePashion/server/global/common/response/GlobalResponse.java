package rePashion.server.global.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GlobalResponse {

    private int status;
    private Object data;

    private GlobalResponse(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    private GlobalResponse(int status) {
        this.status = status;
    }

    public static GlobalResponse of(StatusCode status, Object data){
        return new GlobalResponse(status.getCode(), data);
    }

    public static GlobalResponse of(StatusCode status){
        return new GlobalResponse(status.getCode());
    }
}
