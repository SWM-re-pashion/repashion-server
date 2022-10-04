package rePashion.server.global.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import rePashion.server.global.error.ErrorResponse;
import rePashion.server.global.error.exception.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode exception = (ErrorCode) request.getAttribute("exception");
        setResponse(response, exception);
    }

    private void setResponse(HttpServletResponse response, ErrorCode exception) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(exception);
        response.setContentType("application/json:charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
