package rePashion.server.global.error.exception;

public class DatabaseInsertError extends BusinessException{
    public DatabaseInsertError(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DatabaseInsertError(ErrorCode errorCode) {
        super(errorCode);
    }
}
