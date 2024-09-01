package com.move24.domain.driver.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleNotValidException extends RuntimeException{

    public RoleNotValidException(String message) {
        super(message);
    }

}
