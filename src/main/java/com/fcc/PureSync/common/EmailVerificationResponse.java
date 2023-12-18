package com.fcc.PureSync.common;

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
