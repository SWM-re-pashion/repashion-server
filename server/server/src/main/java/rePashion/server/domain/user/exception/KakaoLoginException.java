package rePashion.server.domain.user.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class KakaoLoginException extends InvalidValueException {
    public KakaoLoginException(ErrorCode errorCode) {
        super(errorCode);
    }
}
