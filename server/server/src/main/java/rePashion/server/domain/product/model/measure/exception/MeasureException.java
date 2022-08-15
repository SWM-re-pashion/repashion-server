package rePashion.server.domain.product.model.measure.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class MeasureException extends InvalidValueException {
    public MeasureException(ErrorCode errorCode) {
        super(errorCode);
    }
}
