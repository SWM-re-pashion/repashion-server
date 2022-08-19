package rePashion.server.domain.statics.category.exception;

import rePashion.server.global.error.exception.BusinessException;
import rePashion.server.global.error.exception.ErrorCode;

public class CategoryNotExisted extends BusinessException {
    public CategoryNotExisted() {
        super(ErrorCode.CATEGORY_NOT_EXISTED);
    }
}
