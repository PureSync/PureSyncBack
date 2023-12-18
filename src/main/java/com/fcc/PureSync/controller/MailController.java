package com.fcc.PureSync.controller;

import com.fcc.PureSync.common.EmailVerificationRequest;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.dto.SignUpbyMailCheckDto;
import com.fcc.PureSync.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {
    //회원가입시 메일 인증 보내기
    //링크 클릭 시 대조

    private final MailService mailService;

    @GetMapping("/verify")
    public ResultDto CheckVerificationCode(@RequestParam("email") String email, @RequestParam("verificationCode") String verificationCode) {
        ResultDto resultDto = mailService.checkVerificationCode(email, verificationCode);
        return resultDto;
    }


}