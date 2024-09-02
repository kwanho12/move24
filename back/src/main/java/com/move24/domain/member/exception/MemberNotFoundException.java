package com.move24.domain.member.exception;

import com.move24.common.exception.CommonException;

public class MemberNotFoundException extends CommonException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
