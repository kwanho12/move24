package com.move24.exception.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileNameNotValidException extends RuntimeException {
    public FileNameNotValidException(String message) {
        super(message);
    }
}