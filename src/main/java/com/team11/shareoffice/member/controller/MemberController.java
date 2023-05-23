package com.team11.shareoffice.member.controller;

import com.team11.shareoffice.member.dto.LoginRequestDto;
import com.team11.shareoffice.member.dto.MessageDto;
import com.team11.shareoffice.member.dto.SignupRequestDto;
import com.team11.shareoffice.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    @GetMapping("/test")
    public String signup() {
        return "안녕하세요!";
    }

    private final MemberService memberService;

    // Sign up
    @Operation(summary = "회원가입 API", description = "회원가입")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "회원 가입 완료")})
    @PostMapping("/signup")
    public ResponseEntity<MessageDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        return memberService.signup(signupRequestDto);
    }

    // Login
    @Operation(summary = "로그인 API", description = "로그인 성공시 jwt 토큰을 헤더에 넣어 반환합니다.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "로그인 완료")})
    @PostMapping("/login")
    public ResponseEntity<MessageDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return memberService.login(loginRequestDto, response);
    }


}
