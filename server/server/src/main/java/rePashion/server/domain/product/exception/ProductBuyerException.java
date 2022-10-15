package rePashion.server.domain.product.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class ProductBuyerException extends InvalidValueException {
    public ProductBuyerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
