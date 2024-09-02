package com.move24.common.exception;

import com.move24.common.response.ApiResponse;
import com.move24.common.response.BusinessPolicyValidErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Map<String, String>> BindExceptionHandler(BindException e) {
        Map<String, String> validErrors = e.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));

        return ApiResponse.of(
                HttpStatus.BAD_REQUEST,
                "parameter 미입력 or 잘못된 유형 검증 오류입니다.",
                validErrors
        );
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessPolicyValidationException.class)
    public ApiResponse<BusinessPolicyValidErrorResponse> businessPolicyValidationExceptionHandler(
            BusinessPolicyValidationException e) {
        return ApiResponse.of(
                HttpStatus.BAD_REQUEST,
                "비즈니스 정책과 관련된 검증 오류입니다.",
                e.getBusinessPolicyValidErrorResponse()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommonException.class)
    public ApiResponse<Object> commonExceptionHandler(CommonException e) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null);
    }
}
