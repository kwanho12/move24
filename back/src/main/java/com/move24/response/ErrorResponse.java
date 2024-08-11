package com.move24.response;

import lombok.Builder;
import lombok.Getter;

/**
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다.",
 * }
 */
@Getter
@Builder
public class ErrorResponse {

    private final String code;
    private final String message;
}
