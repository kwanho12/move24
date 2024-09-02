package com.move24.domain.member.exception;

import com.move24.common.exception.CommonException;

public class IdAlreadyExistsException extends CommonException {
    public IdAlreadyExistsException(String message) {
        super(message);
    }
}
