package com.move24.domain.member.dto.request;

import com.move24.common.utils.validator.EnumValidation;
import com.move24.domain.member.entity.member.Gender;
import com.move24.domain.member.entity.member.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberJoinRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]{6,15}$", message = "ID는 6~15글자의 영문 소문자와 숫자만 입력 가능합니다.")
    private final String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{6,15}$",
            message = "비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해 주세요."
    )
    private final String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 10, min = 2, message = "이름은 2~10자 입력 가능합니다.")
    private final String name;

    @EnumValidation(enumClass = Gender.class, message = "성별 유형이 올바르지 않습니다.", ignoreCase = true)
    private final String gender;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private final String mail;

    @NotBlank(message = "주소를 입력해주세요.")
    @Size(max = 200, min = 5, message = "주소는 5~200자 입력 가능합니다.")
    private final String address;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(
            regexp = "^010-\\d{4}-\\d{4}$",
            message = "휴대폰 번호의 형식이 올바르지 않습니다."
    )
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
