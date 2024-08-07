package com.example.demo.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "member_refresh_token")
public class MemberRefreshToken {
    @Id
    @Column(name = "TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tokenId;

    private Integer userId;

    private String refreshToken;

    private LocalDateTime expiryDate;

    @Builder
    public MemberRefreshToken(Integer userId, String refreshToken, LocalDateTime expiryDate) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expiryDate = expiryDate;
    }
}
