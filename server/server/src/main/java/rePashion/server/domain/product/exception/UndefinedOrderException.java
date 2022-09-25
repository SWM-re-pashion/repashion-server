package rePashion.server.domain.product.exception;

import rePashion.server.global.error.exception.BusinessException;
import rePashion.server.global.error.exception.ErrorCode;

public class UndefinedOrderException extends BusinessException {
    public UndefinedOrderException() {
        super(ErrorCode.ORDER_FILTER_UNDEFINED);
    }
}
