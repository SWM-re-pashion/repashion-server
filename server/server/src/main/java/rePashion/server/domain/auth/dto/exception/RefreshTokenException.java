package rePashion.server.domain.auth.dto.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

import java.util.function.Supplier;

public class RefreshTokenException extends InvalidValueException{
    public RefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
