package rePashion.server.domain.preference.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class FileConvertingException extends InvalidValueException {
    public FileConvertingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
