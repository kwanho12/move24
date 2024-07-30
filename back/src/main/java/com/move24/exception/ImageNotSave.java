package com.move24.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageNotSave extends RuntimeException{
    public ImageNotSave(String message) {
        super(message);
    }
}
