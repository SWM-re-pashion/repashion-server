package rePashion.server.global.error.exception;

public class InvalidJwtTokenException extends BusinessException{
    public InvalidJwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
