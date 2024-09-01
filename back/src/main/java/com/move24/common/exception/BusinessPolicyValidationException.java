package com.move24.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessPolicyValidationException extends CommonException {

    private BusinessPolicyValidErrorResponse businessPolicyValidErrorResponse;

    public BusinessPolicyValidationException(String message, BusinessPolicyValidErrorResponse businessPolicyValidErrorsResponse) {
        super(message);
        this.businessPolicyValidErrorResponse = businessPolicyValidErrorsResponse;
    }
}
