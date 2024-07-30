package com.move24.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExtensionNotMatch extends RuntimeException {
    public ExtensionNotMatch(String message) {
        super(message);
    }
}
