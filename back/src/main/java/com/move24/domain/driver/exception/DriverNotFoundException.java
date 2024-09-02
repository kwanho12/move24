package com.move24.domain.driver.exception;

import com.move24.common.exception.CommonException;

public class DriverNotFoundException extends CommonException {
    public DriverNotFoundException(String message) {
        super(message);
    }
}
