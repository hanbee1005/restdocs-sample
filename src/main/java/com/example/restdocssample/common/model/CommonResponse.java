package com.example.restdocssample.common.model;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Getter
public class CommonResponse<T> extends ExceptionResponse {
    private T data;

    private CommonResponse(@NonNull String code, @NonNull String message, T data) {
        super(code, message);
        this.data = data;
    }

    public static CommonResponse ok() {
        return ok(null);
    }

    public static <T> CommonResponse<T> ok(final T data) {
        return new CommonResponse(Integer.toString(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> CommonResponse<T> created(final T data) {
        return new CommonResponse(Integer.toString(HttpStatus.CREATED.value()), HttpStatus.CREATED.getReasonPhrase(), data);
    }

    public static <T> CommonResponse<T> accepted(final T data) {
        return new CommonResponse(Integer.toString(HttpStatus.ACCEPTED.value()), HttpStatus.ACCEPTED.getReasonPhrase(), data);
    }

    public static <T> CommonResponse<T> accepted() {
        return accepted(null);
    }
}
