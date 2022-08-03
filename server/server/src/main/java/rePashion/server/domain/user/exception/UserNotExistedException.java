package rePashion.server.domain.user.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class UserNotExistedException extends InvalidValueException {
    public UserNotExistedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
