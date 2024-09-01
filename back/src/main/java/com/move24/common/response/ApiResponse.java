package com.move24.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final int code;
    private final String message;
    private final T responseData;

    public ApiResponse(HttpStatus status, String message, T responseData) {
        this.code = status.value();
        this.message = message;
        this.responseData = responseData;
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T responseData) {
        return new ApiResponse<>(httpStatus, message, responseData);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T responseData) {
        return of(httpStatus, httpStatus.name(), responseData);
    }

    public static <T> ApiResponse<T> ok(T responseData) {
        return of(HttpStatus.OK, responseData);
    }
}
