package rePashion.server.domain.statics.exception;

import rePashion.server.global.error.exception.DeveloperException;
import rePashion.server.global.error.exception.ErrorCode;

public class DetailTypeError extends DeveloperException {
    public DetailTypeError() {
        super("해당 category가 ProductDetailService.java 안의 type에서 정의되지 않았습니다");
    }
}
