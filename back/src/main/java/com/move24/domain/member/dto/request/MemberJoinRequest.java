package com.move24.domain.member.dto.request;

import com.move24.common.validator.EnumValidation;
import com.move24.domain.member.entity.member.Gender;
import com.move24.domain.member.entity.member.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberJoinRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private final String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private final String name;

    @EnumValidation(enumClass = Gender.class, message = "성별 유형이 올바르지 않습니다.", ignoreCase = true)
    private final String gender;

    @NotBlank(message = "이메일을 입력해주세요.")
    private final String mail;

    @NotBlank(message = "주소를 입력해주세요.")
    private final String address;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private final String phoneNumber;

    @EnumValidation(enumClass = Role.class, message = "ROLE 유형이 올바르지 않습니다.", ignoreCase = true)
    private final String role;

    @Builder
    private MemberJoinRequest(String userId, String password, String name, String gender, String mail, String address, String phoneNumber, String role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.mail = mail;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public MemberJoinServiceRequest toServiceRequest() {
        return MemberJoinServiceRequest.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .gender(gender)
                .mail(mail)
                .address(address)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }
}
