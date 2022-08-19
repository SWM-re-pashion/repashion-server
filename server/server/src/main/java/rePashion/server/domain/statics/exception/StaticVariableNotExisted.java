package rePashion.server.domain.statics.exception;

import rePashion.server.global.error.exception.BusinessException;
import rePashion.server.global.error.exception.ErrorCode;

public class StaticVariableNotExisted extends BusinessException {
    public StaticVariableNotExisted(String message, ErrorCode errorCode) {
        super(message + " " + errorCode.getMessage(), errorCode);
    }

    public StaticVariableNotExisted(ErrorCode errorCode) {
        super(errorCode);
    }
}
