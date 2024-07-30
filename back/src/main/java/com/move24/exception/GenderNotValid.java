package com.move24.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GenderNotValid extends RuntimeException {
    public GenderNotValid(String message) {
        super(message);
    }
}
