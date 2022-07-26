package rePashion.server.global.common.response;

import lombok.Getter;

@Getter
public enum StatusCode {

    SUCCESS(200),
    CREATED(201);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }
}
