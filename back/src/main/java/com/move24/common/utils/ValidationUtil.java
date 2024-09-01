package com.move24.common.utils;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern USER_ID_PATTERN = Pattern.compile("^[a-z0-9]{6,15}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{6,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^010-\\d{4}-\\d{4}$");

    public static String validateUserId(String userId) {
        if (!USER_ID_PATTERN.matcher(userId).matches()) {
            return "ID는 6~15글자의 영문 소문자와 숫자만 입력 가능합니다.";
        }
        return null;
    }

    public static String validatePassword(String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return "비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해 주세요.";
        }
        return null;
    }

    public static String validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return "이메일 형식이 올바르지 않습니다.";
        }
        return null;
    }

    public static String validatePhoneNumber(String phoneNumber) {
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            return "휴대폰 번호의 형식이 올바르지 않습니다.";
        }
        return null;
    }

    public static String validateName(String name) {
        if(name.length() < 2 || name.length() > 15) {
            return "이름은 2~15자 입력 가능합니다.";
        }
        return null;
    }

    public static String validateAddress(String address) {
        if(address.length() < 5 || address.length() > 200) {
            return "주소는 5~200자 입력 가능합니다.";
        }
        return null;
    }
}
