package com.move24.domain.driver.exception;

import com.move24.common.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DriverNotFoundException extends CommonException {
    public DriverNotFoundException(String message) {
        super(message);
    }
}
