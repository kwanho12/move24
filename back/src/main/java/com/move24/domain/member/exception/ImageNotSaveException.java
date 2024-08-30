package com.move24.domain.member.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageNotSaveException extends RuntimeException{
    public ImageNotSaveException(String message) {
        super(message);
    }
}