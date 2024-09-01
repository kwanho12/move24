package com.move24.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommonException extends RuntimeException {
    public CommonException(String message) {
        super(message);
    }
}
