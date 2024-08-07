package com.example.demo.member.repository;

import com.example.demo.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByMemberEmail(String email); // 이메일로 유저 검색
    boolean existsByMemberEmail(String email); // 이메일로 유저가 존재하는지 검사
}
