package com.move24.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessPolicyValidErrorResponse {
    private final String field;
    private final String message;

    @Builder
    private BusinessPolicyValidErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
