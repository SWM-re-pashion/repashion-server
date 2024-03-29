package rePashion.server.domain.product.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class ProductNotExistedException extends InvalidValueException {
    public ProductNotExistedException() {
        super(ErrorCode.PRODUCT_NOT_EXISTED);
    }
}
