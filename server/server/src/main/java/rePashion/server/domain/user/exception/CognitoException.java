package rePashion.server.domain.user.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class CognitoException extends InvalidValueException {
    public CognitoException(ErrorCode errorCode) {
        super(errorCode);
    }
}
