package com.move24.domain.member.exception;

import com.move24.common.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GenderNotValidException extends CommonException {
    public GenderNotValidException(String message) {
        super(message);
    }
}
