package rePashion.server.domain.auth.dto.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class UserNotExistedException extends InvalidValueException {
    public UserNotExistedException() {
        super(ErrorCode.USER_NOT_EXISTED);
    }
}
