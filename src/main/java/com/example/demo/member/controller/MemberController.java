package com.example.demo.member.controller;

import com.example.demo.global.util.CommonResponse;
import com.example.demo.global.util.DuplicateUserException;
import com.example.demo.global.util.NonExistentUserException;
import com.example.demo.member.dto.JoinMemberDto;
import com.example.demo.member.dto.LoginDto;
import com.example.demo.member.dto.TokenDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.service.JoinService;
import com.example.demo.member.service.LoginService;
import com.example.demo.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // 서비스를 호출시 사용
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final JoinService joinService;
    private final LoginService loginService;

    @GetMapping("/test")
    public CommonResponse test(){
        return new CommonResponse("test");
    }

    @GetMapping("/{memberId}")
    public String getMemberName(@PathVariable("memberId") Integer memberId){
        Member member = memberService.getById(memberId);
        return member.getMemberName();
    }

    @PostMapping("/join")
    public CommonResponse joinMember(@Valid @RequestBody final JoinMemberDto dto){
        CommonResponse commonResponse = new CommonResponse();  // CommonResponse의 경우 아래 소스 참조

        try {
            joinService.joinMember(dto);
        } catch (DuplicateUserException e) {
            // DuplicateUserException의 경우, 이미 회원 가입을 진행한 유저 email일 경우 joinService에서 throw 합니다.
            commonResponse.setError(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return commonResponse; // 성공할 경우 200 status return
    }

    @PostMapping("/login")
    public CommonResponse login(@Valid @RequestBody final LoginDto dto) throws NonExistentUserException {
        // 로그인 서비스에서 로그인을 진행합니다. 회원 정보가 일치합니다면 토큰 DTO를 return
        TokenDto tokenDto = loginService.login(dto);
        return new CommonResponse(tokenDto);
    }
    @PostMapping("/reissue")
    public CommonResponse reissue(HttpServletRequest request) throws BadRequestException {

        TokenDto tokenDto = loginService.reissue(request);
        return new CommonResponse(tokenDto);
    }

}
