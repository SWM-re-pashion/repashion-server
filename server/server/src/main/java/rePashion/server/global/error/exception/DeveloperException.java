package rePashion.server.global.error.exception;

public class DeveloperException extends RuntimeException{

    private final ErrorCode errorCode = ErrorCode.DEVELOPER_INTENSE_FAULT;

    public DeveloperException(String message){
        super(message + " 이 오류가 계속될 시에 개발자에게 문의하세요");
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
