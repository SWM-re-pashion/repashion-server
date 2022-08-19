package rePashion.server.domain.statics.exception;

import rePashion.server.global.error.exception.BusinessException;
import rePashion.server.global.error.exception.ErrorCode;

public class ParameterNotCorrect extends BusinessException {
    public ParameterNotCorrect(ErrorCode errorCode) {
        super(errorCode);
    }
}
