package rePashion.server.domain.statics.exception;

import rePashion.server.global.error.exception.BusinessException;
import rePashion.server.global.error.exception.ErrorCode;

public class StaticVariableNotExisted extends BusinessException {
    public StaticVariableNotExisted(ErrorCode errorCode) {
        super(errorCode);
    }
}
