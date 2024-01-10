package com.fcc.PureSync.context.sendmail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationResponse {
    private String memEmail;
    private String verificationCode;
}
