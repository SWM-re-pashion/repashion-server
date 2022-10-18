package rePashion.server.domain.auth.dto.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class CookieNotExistedException extends InvalidValueException {
    public CookieNotExistedException() {
        super(ErrorCode.COOKIE_NOT_EXISTED);
    }
}
