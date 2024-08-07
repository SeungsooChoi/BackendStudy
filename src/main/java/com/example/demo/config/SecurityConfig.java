package com.example.demo.config;

import com.example.demo.global.util.CustomerAccessDeniedHandler;
import com.example.demo.global.util.CustomerAuthenticationEntryPoint;
import com.example.demo.global.util.JwtAuthenticationFilter;
import com.example.demo.global.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration          // Spring 설정 클래스임을 나타내는 어노테이션
@EnableWebSecurity      // Spring Security 활성화하는 어노테이션
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.example.demo") // 가장 최상위 package
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    // 아래 기재된 엔드포인트는 인증 없이 접근
    private final static String[] PERMIT_ALL = {
            "/member/join",    // 회원가입 API
            "/member/login",   // 로그인 API
            "/member/reissue",  // refresh token을 통한 토큰 재발급
            "/todo"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)                  // csrf 보안 disable
                .formLogin(AbstractHttpConfigurer::disable)             // formLogin disable
                .httpBasic(AbstractHttpConfigurer::disable)             // BasicHttp disable
                // PERMIT_ALL : 엔드포인트는 인증 없이 접근할 수 있도록 설정
//                .authorizeHttpRequests(requests -> requests.requestMatchers(PERMIT_ALL)
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated());
                // 토큰을 통한 로그인. 사용 X session stateless
                .sessionManagement((sessionManager) ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 로그아웃 관련

                // 권한 없이 접근할 api
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(PERMIT_ALL).permitAll().anyRequest().authenticated())

                // 로그인 관련
                // addFilterBefore: UsernamePasswordAuthenticationFilter 전에 jwt Token 관련 Filter 진행
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)

                // 인증, 인가와 관련된 예외 처리 추가
                .exceptionHandling(exceptionHandling  -> exceptionHandling
                    .accessDeniedHandler(new CustomerAccessDeniedHandler())  // AccessDeniedHandler 설정
                    .authenticationEntryPoint(new CustomerAuthenticationEntryPoint())  // AuthenticationEntryPoint 설정
                );
        return http.build();
    }

    // BCryptPasswordEncoder를 빈으로 등록하여 비밀번호를 안전하게 암호화는 것에 사용
    // 다양한 방법이 있지만 BCryptPasswordEncoder는 강력한 해시 알고리즘을 제공합니다.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
