package com.fcc.PureSync.context.member.controller;

import com.fcc.PureSync.context.member.dto.FindPasswordDto;
import com.fcc.PureSync.context.member.dto.LoginDto;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.member.dto.SignupDto;
import com.fcc.PureSync.context.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResultDto signup(@RequestBody @Valid SignupDto signupDto) {
        return memberService.signup(signupDto);
    }

    @PostMapping("/login")
    public ResultDto login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        return memberService.login(loginDto,response);
    }

    @GetMapping("/check-duplicate/{field}/{value}")
    public ResultDto checkDuplicate(@PathVariable @NotBlank String field, @PathVariable String value) {
        return memberService.checkDuplicate(field, value);
    }

    @PostMapping("/searchPassword")
    public ResultDto searchPassword(@RequestBody @Valid FindPasswordDto findPasswordDto) {
        return memberService.searchPassword(findPasswordDto);
    }

    @GetMapping("/searchId/{memEmail}")
    public ResultDto searchId(@PathVariable("memEmail") @Email String memEmail) {
        return memberService.searchId(memEmail);
    }

}