package rePashion.server.domain.auth.dto.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class CognitoGrantException extends InvalidValueException {
    public CognitoGrantException(ErrorCode errorCode) {
        super(errorCode);
    }
}
