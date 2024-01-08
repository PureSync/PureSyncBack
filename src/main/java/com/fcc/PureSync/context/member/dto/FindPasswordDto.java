package com.fcc.PureSync.context.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindPasswordDto {
    @Pattern(regexp =  "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String memId;
    @Email
    private String memEmail;
}
