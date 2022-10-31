package rePashion.server.domain.product.exception;

import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidValueException;

public class ProductStatusAlreadyUpdated extends InvalidValueException {
    public ProductStatusAlreadyUpdated() {
        super(ErrorCode.PRODUCT_STATUS_ALREADY_UPDATED);
    }
}
