package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.FindPasswordDto;
import com.fcc.PureSync.dto.LoginDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.dto.SignupDto;
import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup") // 회원가입
    public ResultDto signup (@RequestBody @Valid SignupDto signupDto) {
        return memberService.signup(signupDto);
    }

    @PostMapping("/login")  // 로그인
    public ResultDto login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        return memberService.login(loginDto);
    }

    @GetMapping("/check-duplicate/{field}/{value}")
    public ResultDto checkDuplicate(@PathVariable @NotBlank String field, @PathVariable  String value) {
        return memberService.checkDuplicate(field, value);
    }

    @PostMapping("/searchPassword")
    public ResultDto searchPassword(@RequestBody @Valid FindPasswordDto findPasswordDto){
    return memberService.searchPassword(findPasswordDto);
    }

    @GetMapping("/searchId/{memEmail}")
        public ResultDto searchId(@PathVariable("memEmail") @Email String memEmail){
        return memberService.searchId(memEmail);
    }


    @GetMapping("/token")
    public HashMap<String, Object> token(@AuthenticationPrincipal CustomUserDetails userDetails) {
        HashMap<String, Object> map = new HashMap<>();
        String memId = userDetails.getUsername();
        Long memSeq = userDetails.getMemSeq();
        map.put("memId", memId);
        map.put("memSeq", memSeq);
        return map;
    }



}