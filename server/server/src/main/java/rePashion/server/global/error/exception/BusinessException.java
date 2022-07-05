package rePashion.server.global.error.exception;

public class BusinessException extends RuntimeException{

    /*
        BusinessException은 RuntimeException의 자식 클래스로
        개발자가 직접 정의하는 예외상황 중 가장 상위 클래스입니다
        예외 상황을 새로 만들시에
        BusinessException을 상속받아서 만드시면 됩니다
     */

    private ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
