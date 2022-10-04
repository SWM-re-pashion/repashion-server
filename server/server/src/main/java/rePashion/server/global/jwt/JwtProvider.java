package rePashion.server.global.jwt;

import rePashion.server.domain.user.model.User;

import javax.servlet.http.HttpServletRequest;

public interface JwtProvider {

    public String parse(User user); // 토큰을 만드는 메소드

    public JwtTokenDto parsing(String token);   // 토큰을 파싱하는 메소드

    public Long getPk(String token);

    public String resolve(HttpServletRequest request);  // 헤더에서 토큰 정보를 가져오는 메소드
}
