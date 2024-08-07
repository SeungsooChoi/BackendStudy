package com.example.demo.global.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter
    // 동일한 request 안에서 한번만 실행되는 필터로, 현재 프로젝트에선 jwt token 인증에 사용된다
    // UsernamePasswordAuthenticationFilter 전에 authentication 객체를 셋팅해 둔다
    // 해당 filter의 경우 api/a가 끝난후 api/b로 리다이렉트 될 경우 Filter가 두번 호출되거나 하는 것을 막을 수 있다

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = TokenUtil.parsingToken(authHeader);
        if(! StringUtils.isEmpty(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);  // 만약 토큰이 유효하지 않을 경우, Excpetion이 발생해 인증이 실패
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 중요한 점은, 위에서 token 값이 유효해 인증에 성공했다면, SecurityContextHolder에 Authentication 값이 세팅 된 채 다음 필터로 넘어간다는 점
        filterChain.doFilter(request, response);
    }
}