package com.move24.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileNameNotValid extends RuntimeException {
    public FileNameNotValid(String message) {
        super(message);
    }
}
