package com.move24.common.response;

import lombok.Builder;
import lombok.Getter;

/**
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다.",
 * }
 */
@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    @Builder
    private ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
