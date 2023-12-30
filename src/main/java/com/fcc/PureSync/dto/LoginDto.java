package com.fcc.PureSync.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class LoginDto {
    @Pattern(regexp =  "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String memId;
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    private String memPassword;
}
