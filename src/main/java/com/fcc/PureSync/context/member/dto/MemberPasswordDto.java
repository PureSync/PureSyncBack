package com.fcc.PureSync.context.member.dto;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPasswordDto {
    String oldPassword;
    String newPassword;
}
