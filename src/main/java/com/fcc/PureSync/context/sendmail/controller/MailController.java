package com.fcc.PureSync.context.sendmail.controller;

import com.fcc.PureSync.core.constant.EmailConstant;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.sendmail.service.MailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {
    //회원가입시 메일 인증 보내기
    //링크 클릭 시 대조

    private final MailService mailService;

    @GetMapping("/verify")
    public ResultDto CheckVerificationCode(@RequestParam("email") String email, @RequestParam("verificationCode") String verificationCode
            , HttpServletResponse response) throws IOException {
        ResultDto resultDto = mailService.checkVerificationCode(email, verificationCode);
        response.sendRedirect(EmailConstant.AWS_REVERSE_PROXY_DOMAIN +"/landing");
        return resultDto;
    }


}