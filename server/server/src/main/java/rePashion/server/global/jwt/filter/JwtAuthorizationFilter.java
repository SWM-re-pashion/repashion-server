package rePashion.server.global.jwt.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import rePashion.server.global.error.exception.ErrorCode;
import rePashion.server.global.error.exception.InvalidJwtTokenException;
import rePashion.server.global.jwt.JwtTokenDto;
import rePashion.server.global.jwt.impl.AccessTokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

    private final AccessTokenProvider accessTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String resolvedToken = accessTokenProvider.resolve((HttpServletRequest) request);
        try{
            setAuthentication(resolvedToken);
        }catch (InvalidJwtTokenException e){
            request.setAttribute("exception", e.getErrorCode());
        }
        catch (Exception e){
            request.setAttribute("exception", ErrorCode.HANDLE_ACCESS_DENIED);
        }
        chain.doFilter(request, response);
    }

    private void setAuthentication(String resolvedToken) throws Exception {
        if(resolvedToken == null)   throw new Exception();
        Authentication authentication = accessTokenProvider.getAuthentication(resolvedToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
