package com.move24.common.exception;

import com.move24.common.response.ErrorResponse;
import com.move24.common.response.ValidationErrorResponse;
import com.move24.domain.driver.exception.DriverNotFoundException;
import com.move24.domain.member.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ValidationErrorResponse MethodArgumentNotValidExceptionHandler(BindException e) {
        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .code("400")
                .message("검증 오류입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ExtensionNotMatchException.class,
            GenderNotValidException.class,
            IdAlreadyExistsException.class,
            FileNotFoundException.class,
            ImageNotSaveException.class,
            DriverNotFoundException.class})
    public ErrorResponse runtimeExceptionHandler(RuntimeException e) {
        return ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();
    }
}
