package com.example.demo.member.service;

import com.example.demo.global.constant.AuthConstant;
import com.example.demo.global.util.JwtTokenProvider;
import com.example.demo.global.util.NonExistentUserException;
import com.example.demo.global.util.TokenUtil;
import com.example.demo.member.dto.LoginDto;
import com.example.demo.member.dto.TokenDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberRefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberService memberService;
    private final MemberRefreshTokenService memberRefreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenDto login(LoginDto dto) throws NonExistentUserException {
        String email = dto.getMemberEmail();
        // 해당 이메일의 유저가 존재하는지 검사
        if(!memberService.existByEmail(email)) {
            throw new NonExistentUserException("로그인 정보를 확인해주세요.");   // 직접 정의한 Exception. 아래 소스 참조.
        }

        Member user = memberService.getByEmail(email);
        // dto로 입력한 패스워드와, user 테이블에서 가져온 비밀빈호가 일치 여부 검사
        if(! passwordEncoder.matches(dto.getMemberPassword(), user.getMemberPassword())) {
            throw new BadCredentialsException("비밀번호를 확인해주세요.");
        }

        // 마지막 로그인 일시 업데이트
        user.updateLastLoginDate();
        memberService.save(user);

        // 비밀번호가 일치합니다면, JwtTokenProvider 서비스에게 토큰 발급 요청
        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(email, user.getMemberAuthority());
        memberRefreshTokenService.saveTokenByTokenDto(user.getMemberId(), tokenDto);

        return tokenDto;
    }

    // refreshToken을 통한 재발급
    @Transactional
    public TokenDto reissue(HttpServletRequest request) throws BadRequestException {
        String requestHeader = request.getHeader(AuthConstant.AUTH_HEADER);
        if(StringUtils.isEmpty(requestHeader)) {
            throw new BadRequestException("잘못된 요청입니다.");
        }

        // check refreshToken
        String token = TokenUtil.parsingToken(requestHeader);
        if(!memberRefreshTokenService.isExists(token)) {
            throw new BadRequestException("잘못된 요청입니다.");
        }

        MemberRefreshToken refreshToken = memberRefreshTokenService.getByToken(token);
        LocalDateTime expiryDate = refreshToken.getExpiryDate();
        if(expiryDate.isBefore(LocalDateTime.now())) {
            // 사용 기간이 지난 refreshToken
            memberRefreshTokenService.deleteByToken(token);
            throw new BadRequestException("잘못된 요청입니다.");
        }

        // 정상 동작하는 refreshToken // 새로 발급 후 삭제
        Integer userId = refreshToken.getUserId();
        Member user = memberService.getById(userId);

        // access Token 재발급 후 삭제
        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(user.getMemberEmail(), user.getMemberAuthority());
        memberRefreshTokenService.saveTokenByTokenDto(user.getMemberId(), tokenDto);
        memberRefreshTokenService.deleteByToken(token);
        return tokenDto;
    }
}