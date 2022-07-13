package rePashion.server.domain.user.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class FileConvertingException extends InvalidValueException {
    public FileConvertingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
