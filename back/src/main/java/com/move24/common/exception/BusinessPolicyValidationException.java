package com.move24.common.exception;

import lombok.Getter;

@Getter
public class BusinessPolicyValidationException extends CommonException {

    private final BusinessPolicyValidErrorResponse businessPolicyValidErrorResponse;

    public BusinessPolicyValidationException(String message, String field) {
        super(message);
        this.businessPolicyValidErrorResponse = BusinessPolicyValidErrorResponse.builder()
                .field(field)
                .message(message)
                .build();
        ;
    }
}
