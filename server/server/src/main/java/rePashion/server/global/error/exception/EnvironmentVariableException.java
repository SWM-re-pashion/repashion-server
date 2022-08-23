package rePashion.server.global.error.exception;

public class EnvironmentVariableException extends BusinessException{
    public EnvironmentVariableException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public EnvironmentVariableException(ErrorCode errorCode) {
        super(errorCode);
    }
}
