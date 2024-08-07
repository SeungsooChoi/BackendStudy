package com.example.demo.global.util;

import com.example.demo.global.constant.AuthConstant;
import com.example.demo.member.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.expire}")
    private int ACCESS_TOKEN_EXPIRE_TIME;   // 30분
    @Value("${jwt.refresh.expire}")
    private int REFRESH_TOKEN_EXPIRE_TIME;  // 7일

    private final Key key;

    private static final String TOKEN_AUTH_KEY = "auth";

    // 컴포넌트 스캔을 통한 싱글톤 등록시 secretKey 값을 Base64로 디코딩하여 key[]로 세팅합니다
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    public Authentication getAuthentication(String accessToken) {
        Claims claims = getClaims(accessToken);

        // 권한 체크
        String authority = claims.get(TOKEN_AUTH_KEY).toString();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<? extends GrantedAuthority> authorities = List.of(simpleGrantedAuthority);

        UserDetails principal = new User(claims.getSubject(), "", authorities);   // 차후 컨트롤러에선 Principal로 유저 정보에 접근할 수 있다
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Claims getClaims(String token) {
        try{
            // token expire time 지났을 경우, ExpiredJwtException 반환
            return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("getClaims Error: {}",  e.getClass().getName());
            throw new AccessDeniedException("잘못된 요청입니다.");  // ExpiredJwtException 포함
        }
    }

    /**
     * JWT 토큰 발급
     * @param email 유저 이메일. 차후 스프링 컨텍스트의 Authentication 안에 userName 값에 세팅된다
     * @param authority 유저 권한
     * @return TokenDto
     */
    public TokenDto generateTokenDto(String email, String authority) {
        Date now = Calendar.getInstance().getTime();

        Date accessTokenExpireDate = DateUtils.addMilliseconds(now, ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpireDate = DateUtils.addMilliseconds(now, REFRESH_TOKEN_EXPIRE_TIME);
        String accessToken = getToken(email, authority, accessTokenExpireDate);
        String refreshToken = getToken(email, authority, refreshTokenExpireDate);

        LocalDateTime accessTokenExpire = accessTokenExpireDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime refreshTokenExpire = refreshTokenExpireDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return TokenDto.builder()
                .grantType(AuthConstant.TOKEN_TYPE)
                .accessToken(accessToken)
                .accessTokenExpire(accessTokenExpire)
                .refreshToken(refreshToken)
                .refreshTokenExpire(refreshTokenExpire)
                .build();
    }

    private String getToken(String subject, String authority, Date expireDate) {
        return Jwts.builder()
                .claim("auth", authority)
                .setIssuedAt(expireDate)
                .setExpiration(expireDate)
                .setSubject(subject)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}