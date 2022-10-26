package rePashion.server.domain.preference.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

import java.util.function.Supplier;

public class PreferenceNotExistedException extends InvalidValueException{

    public PreferenceNotExistedException() {
        super(ErrorCode.PREFERENCE_NOT_EXISTED);
    }
}
