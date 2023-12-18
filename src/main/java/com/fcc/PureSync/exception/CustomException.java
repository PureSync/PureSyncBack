package com.fcc.PureSync.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private final  CustomExceptionCode exceptionCode;


}