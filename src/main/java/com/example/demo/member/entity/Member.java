package com.example.demo.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
public class Member{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    private int memberId;

    @Column(nullable = false)
    private String memberPassword;

    @Column(nullable = false) // 개발자들끼리 알아야 함
    private String memberName;

    @Column(nullable = false)
    private String memberEmail;

    private String memberAuthority;

    private LocalDateTime lastLoginDate;

    @Builder
    public Member(String email, String password, String memberName) {
        // 일반 유저 회원가입
        this.memberEmail = email;
        this.memberPassword = password;
        this.memberName = memberName;
        this.memberAuthority = "ROLE_USER";    // 일반 유저의 권한. 우선 이 값을 "MEMBER_ROLE" 혹은 "ROLE_USER"로 사용하자
    }

    public void updateLastLoginDate() {
        this.lastLoginDate = LocalDateTime.now();
    }
}
