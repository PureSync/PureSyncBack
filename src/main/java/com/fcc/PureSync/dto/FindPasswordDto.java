package com.fcc.PureSync.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindPasswordDto {
    private String memId;
    private String memEmail;
}
