package rePashion.server.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class JwtTokenDto {
    private final Long userId;
    private final String authority;
    private final Date issuedAt;
    private final Date expiredAt;
}
