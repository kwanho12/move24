package com.move24.domain.member.exception;

import com.move24.common.exception.CommonException;

public class ExtensionNotMatchException extends CommonException {
    public ExtensionNotMatchException(String message) {
        super(message);
    }
}
