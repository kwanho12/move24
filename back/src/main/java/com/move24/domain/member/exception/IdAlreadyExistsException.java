package com.move24.domain.member.exception;

import com.move24.common.exception.CommonException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdAlreadyExistsException extends CommonException {
    public IdAlreadyExistsException(String message) {
        super(message);
    }
}
