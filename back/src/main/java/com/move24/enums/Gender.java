package com.move24.enums;

import com.move24.exception.exception.GenderNotValidException;

import java.util.Arrays;

public enum Gender {
    MALE, FEMALE;

    public static void isValidGender(String gender) {
       boolean isValid = Arrays.stream(Gender.values())
                .anyMatch(value -> value.name().equalsIgnoreCase(gender));
        if (!isValid) {
            throw new GenderNotValidException("유효하지 않은 성별 형식입니다.");
        }
    }
}
