package com.example.demo.member.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // parameter로 들어오는 email로 이미 가입된 유저가 있는지 검사
    public boolean existByEmail(String email) {
        return memberRepository.existsByMemberEmail(email);
    }

    // userId를 통한 유저 검색
    public Member getById(int userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    // email를 통한 유저 검색
    public Member getByEmail(String email) {
        return memberRepository.findByMemberEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    // 차후 회원가입 혹은 유저 수정 등, 유저 데이터를 저장할 때 사용된다.
    @Transactional
    public void save(Member bobUser) {
        memberRepository.save(bobUser);
    }
}
