package rePashion.server.domain.user.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class UserNotFoundException extends InvalidValueException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
