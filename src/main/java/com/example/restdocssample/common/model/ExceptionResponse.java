package com.example.restdocssample.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    @NonNull
    private String code;
    @NonNull
    private String message;

    public static ExceptionResponse of(RuntimeException businessException) {
        return new ExceptionResponse(
                businessException.getMessage(),  // TODO 별도 코드 지정 필요
                businessException.getMessage()
        );
    }
}
