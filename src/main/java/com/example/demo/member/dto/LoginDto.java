package com.example.demo.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    private String memberEmail;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String memberPassword;
}
