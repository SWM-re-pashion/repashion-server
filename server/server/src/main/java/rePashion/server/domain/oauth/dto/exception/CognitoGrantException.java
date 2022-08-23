package rePashion.server.domain.oauth.dto.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class CognitoGrantException extends InvalidValueException {
    public CognitoGrantException() {
        super(ErrorCode.COGNITO_GRANT_ERROR);
    }
}
