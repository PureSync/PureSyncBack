package com.fcc.PureSync.dto;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPasswordDto {
    String oldPassword;
    String newPassword;
}
