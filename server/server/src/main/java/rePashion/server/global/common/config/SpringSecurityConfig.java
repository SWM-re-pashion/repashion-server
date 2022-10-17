package rePashion.server.global.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import rePashion.server.global.jwt.filter.JwtAuthorizationFilter;
import rePashion.server.global.jwt.impl.AccessTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AccessTokenProvider accessTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable();

        http.authorizeRequests()
                .antMatchers("/health-check").permitAll()
                .antMatchers("/statics/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()       //api/auth/**는 로그인, 중복 id 검사 등등 이므로 모든 권한을 가진 자들에게 접근을 풀어줌
                .antMatchers("/api/category/**").permitAll()
                .antMatchers("/api/shop/**").permitAll()       //api/shop/**는 shop 상품 정보 보기, shop 검색 기능 등등 이므로 모든 권한을 가진 자들에게 접근을 풀어줌
                .anyRequest().authenticated()                              // 그 밖에 모든 요청은 jwt를 통해서
                .and()
                .addFilterBefore(new JwtAuthorizationFilter(accessTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new SecurityAuthenticationEntryPoint());
    }
}
