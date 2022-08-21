package rePashion.server.domain.preference.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class StyleImageNotExistedException extends InvalidValueException {
    public StyleImageNotExistedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
