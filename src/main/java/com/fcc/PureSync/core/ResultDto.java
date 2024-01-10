package com.fcc.PureSync.core;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    int code;
    HttpStatus httpStatus;
    String message;
    HashMap<String, Object> data;

    public static ResultDto of(int code, HttpStatus httpStatus, String message, HashMap<String, Object> data) {
        return new ResultDto(code, httpStatus, message, data);
    }
}
