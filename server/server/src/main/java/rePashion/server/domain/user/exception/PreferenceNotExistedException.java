package rePashion.server.domain.user.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

import java.util.function.Supplier;

public class PreferenceNotExistedException extends InvalidValueException{

    public PreferenceNotExistedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
