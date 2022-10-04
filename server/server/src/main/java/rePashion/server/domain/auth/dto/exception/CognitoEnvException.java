package rePashion.server.domain.auth.dto.exception;
;
import rePashion.server.global.error.exception.EnvironmentVariableException;
import rePashion.server.global.error.exception.ErrorCode;

public class CognitoEnvException extends EnvironmentVariableException {
    public CognitoEnvException() {
        super(ErrorCode.COGNITO_ENVIRONMENT_ERROR);
    }
}
