package com.move24.request;

import com.move24.enums.Gender;
import com.move24.enums.Role;
import com.move24.utils.validation.EnumValidation;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class JoinRequest {

    @NotBlank(message = "아이디는 필수로 입력해야 합니다.")
    @Pattern(regexp = "^[a-z0-9]{6,15}$", message = "ID는 6~15글자의 영문 소문자와 숫자만 입력 가능합니다.")
    private String memberId;

    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{6,15}$",
            message = "비밀번호는 6~15자로 영문, 숫자, 특수기호를 혼용해서 입력해 주세요."
    )
    private String password;

    @NotBlank(message = "이름은 필수로 입력해야 합니다.")
    @Size(max = 10, min = 2, message = "이름은 2~10자 입력 가능합니다.")
    private String name;

    @EnumValidation(enumClass = Gender.class, message = "성별 유형이 올바르지 않습니다.", ignoreCase = true)
    private String gender;

    @Email
    @NotNull
    private String mail;

    @NotBlank(message = "주소는 필수로 입력해야 합니다.")
    @Size(max = 200, min = 5, message = "주소는 5~200자 입력 가능합니다.")
    private String address;

    @NotBlank(message = "휴대폰 번호는 필수로 입력해야 합니다.")
    @Pattern(
            regexp = "^010-\\d{4}-\\d{4}$",
            message = "휴대폰 번호의 형식이 올바르지 않습니다."
    )
    private String phoneNumber;

    @EnumValidation(enumClass = Role.class, message = "ROLE 유형이 올바르지 않습니다.", ignoreCase = true)
    private String role;

}
