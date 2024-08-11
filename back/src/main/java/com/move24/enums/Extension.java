package com.move24.enums;

import com.move24.exception.exception.ExtensionNotMatchException;

import java.util.Arrays;

public enum Extension {
    PNG, JPG, JPEG, GIF, WEBP, TIF;

    public static void isValidExtension(String extension) {
        boolean isValid = Arrays.stream(Extension.values())
                .anyMatch(value -> value.name().equalsIgnoreCase(extension));
        if (!isValid) {
            throw new ExtensionNotMatchException(extension.substring(1) + "은(는) 이미지 확장자가 아닙니다.");
        }
    }
}
