package com.move24.exception;

import com.move24.exception.exception.IdAlreadyExistsException;
import com.move24.response.ErrorResponse;
import com.move24.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IdAlreadyExistsException.class)
    public ErrorResponse IdAlreadyExistsExceptionHandler(IdAlreadyExistsException e) {
        return ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();
    }
}
