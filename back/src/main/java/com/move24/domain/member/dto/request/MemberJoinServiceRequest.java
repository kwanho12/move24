package com.move24.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

import static com.move24.common.utils.ValidationUtil.*;

@Getter
public class MemberJoinServiceRequest {

    private final String userId;
    private final String password;
    private final String name;
    private final String gender;
    private final String mail;
    private final String address;
    private final String phoneNumber;
    private final String role;

    @Builder
    private MemberJoinServiceRequest(String userId, String password, String name, String gender, String mail, String address, String phoneNumber, String role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.mail = mail;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void validateBusinessPolicyException() {
        validateRegEx("userId", userId, USER_ID_PATTERN, "ID는 6~15글자의 영문 소문자와 숫자만 입력 가능합니다.");
        validateRegEx("password", password, PASSWORD_PATTERN, "비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해 주세요.");
        validateName("name", name, "이름은 2~15자 입력 가능합니다.");
        validateRegEx("email", mail, EMAIL_PATTERN, "이메일 형식이 올바르지 않습니다.");
        validateAddress("address", address, "주소는 5~200자 입력 가능합니다.");
        validateRegEx("phoneNumber", phoneNumber, PHONE_NUMBER_PATTERN, "휴대폰 번호의 형식이 올바르지 않습니다.");
    }
}
