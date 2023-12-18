package com.fcc.PureSync.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private final String error;
    private final String message;

    public static ResponseEntity<ErrorResponse> errorResponse(CustomExceptionCode customExceptionCode) {

        return ResponseEntity
                .status(customExceptionCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .error(customExceptionCode.name())
                        .message(customExceptionCode.getMessage())
                        .build());
    }
}
