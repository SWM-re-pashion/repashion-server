package rePashion.server.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)        //Jackson 라이브러리에서 제공하는 어노테이션으로 JSON 응답값의 형식을 지정할 때 사용
public enum ErrorCode {
    // Common, 공통 error code

    /*
    Error Status 간단 정리
    400, Bad Request : 잘못된 문법으로 인해서 서버가 요청을 이해할 수 없을때 사용
    401, Unauthorized : 인증되지 않은 클라이언트의 요청이 들어왔을때 사용
    403, Forbidden : 클라이언트가 콘텐츠에 접근할 권리를 가지고 있지 않을때 사용
    404, Not Found : 클라이언트가 잘못된 url로 접근했을때 사용
    ref. https://www.whatap.io/ko/blog/40/
     */

    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    DEVELOPER_INTENSE_FAULT(403, "D001", "치명적 오류"),

    //회원가입
    USER_SIGNUP_ERROR(400, "U001", "회원가입시에 오류가 발생하여 테이블에 저장되지 않았습니다"),

    //코그니토
    COGNITO_ENVIRONMENT_ERROR(500,"COG_ENV_ERROR", "SERVER_ERROR::코그니토 환경변수 확인하세요"),
    COGNITO_GRANT_ERROR(400, "COG_GRANT_ERROR", "CLIENT_ERROR::인가코드를 다시 한번 확인하세요"),

    USER_NOT_EXISTED(400, "USER_NOT_EXISTED", "USER가 존재하지 않습니다"),

    //개인화 추천
    STYLE_IMAGE_NOT_EXISTED(400, "STYLE_IMAGE_NOT_EXISTED", "해당 이미지가 존재하지 않습니다"),
    SIZE_NOT_EXISTED(400, "U003", "해당 사이즈는 존재하지 않습니다"),

    //정적 변수
    STATIC_VARIABLE_NOT_EXISTED(400, "STATIC01", "해당 정적 변수가 존재하지 않습니다"),
    CATEGORY_NOT_EXISTED(400, "STATIC02", "해당 카테고리가 존재하지 않습니다"),

    //상품
    PRODUCT_NOT_EXISTED(400, "P001", "해당 상품이 존재하지 않습니다"),

    //파일 업로드
    FILE_CONVERTING_ERROR(400, "F001", "파일 업로드 시에 오류가 발생했습니다"),

    //DB 오류
    DB_INSERTING_ERROR(400, "DB001", "DB에 데이터 추가 시에 오류가 발생했습니다"),

    //Measure 오류
    MEASURE_DATA_ERROR(400, "MEASURE001", "예약된 측정값의 개수 혹은 형식이 올바르지 않습니다"),

    // filter

    ORDER_FILTER_UNDEFINED(400, "ORDER_FILTER_UNDEFINED", "해당 Order가 정의되지 않았습니다"),

    // jwt 토큰 오류
    TOKEN_EXPIRED(403, "TOKEN_EXPIRED", "token has expired."),
    INVALID_SIGNATURE_TOKEN(403, "INVALID_SIGNATURE", "The token's signature information is incorrect."),
    MISMATCH_ALGORITHM(403, "MISMATCH_ALGORITHM", "The token encryption algorithm is incorrect."),
    INVALID_TOKEN(403, "INVALID_TOKEN", "Invalid formatted token."),
    INVALID_REFRESH_TOKEN(403, "INVALID_REFRESH_TOKEN", "Invalid refresh token"),

    //Refresh token

    REFRESH_TOKEN_NOT_EXISTED(400, "REFRESH_TOKEN_NOT_EXISTED", "refresh token not existed"),
    REFRESH_TOKEN_NOT_MATCH(400, "REFRESH_TOKEN_NOT_MATCH", "refresh token is not matched");


    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
