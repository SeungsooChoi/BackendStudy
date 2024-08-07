package com.example.demo.member.service;

import com.example.demo.member.dto.TokenDto;
import com.example.demo.member.entity.MemberRefreshToken;
import com.example.demo.member.repository.MemberRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRefreshTokenService {

    private final MemberRefreshTokenRepository memberRefreshTokenRepository;

    public boolean isExists(String refreshToken) {
        return memberRefreshTokenRepository.existsByRefreshToken(refreshToken);
    }

    public MemberRefreshToken getByToken(String refreshToken) {
        Optional<MemberRefreshToken> optionToken = memberRefreshTokenRepository.findOneByRefreshToken(refreshToken);
        return optionToken.orElseThrow(() -> new BadCredentialsException("토큰 정보가 잘못됐습니다."));
    }

    @Transactional
    public void saveTokenByTokenDto(int userId, TokenDto tokenDto) {
        String refreshToken = tokenDto.getRefreshToken();
        LocalDateTime refreshTokenExpire = tokenDto.getRefreshTokenExpire();

        MemberRefreshToken userRefreshToken = MemberRefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .expiryDate(refreshTokenExpire).build();
        memberRefreshTokenRepository.save(userRefreshToken);
    }

    @Transactional
    public void deleteByToken(String refreshToken) {
        memberRefreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

    public List<MemberRefreshToken> getAllAlreadyExpired(){
        return memberRefreshTokenRepository.findAllByExpiryDateBefore(LocalDateTime.now());
    }

    @Transactional
    public void deleteAll(List<MemberRefreshToken> refreshTokens) {
        memberRefreshTokenRepository.deleteAll(refreshTokens);
    }
}