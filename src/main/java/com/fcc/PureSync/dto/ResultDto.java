package com.fcc.PureSync.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    Integer code;
    HttpStatus httpStatus;
    String message;
    HashMap<String, Object> data;
}
