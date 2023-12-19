package com.fcc.PureSync.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class LoginDto {

    private String memId;
    private String memPassword;
}
