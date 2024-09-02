package com.move24.common.utils;

import com.move24.common.exception.BusinessPolicyValidationException;

import java.util.regex.Pattern;

public class ValidationUtil {

    public static final Pattern USER_ID_PATTERN = Pattern.compile("^[a-z0-9]{6,15}$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{6,15}$");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^010-\\d{4}-\\d{4}$");

    public static void validateRegEx(String field, String parameter, Pattern pattern, String message) {
        if (!pattern.matcher(parameter).matches()) {
            throw new BusinessPolicyValidationException(message, field);
        }
    }

    public static void validateName(String field, String parameter, String message) {
        if(parameter.length() < 2 || parameter.length() > 15) {
            throw new BusinessPolicyValidationException(message, field);
        }
    }

    public static void validateAddress(String field, String parameter, String message) {
        if(parameter.length() < 5 || parameter.length() > 200) {
            throw new BusinessPolicyValidationException(message, field);
        }
    }
}
