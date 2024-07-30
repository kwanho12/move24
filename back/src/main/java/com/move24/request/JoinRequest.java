package com.move24.request;

import com.move24.enums.Gender;
import com.move24.enums.MemberStatus;
import com.move24.utils.validation.EnumValidation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class JoinRequest {

    private String memberId;
    private String password;
    private String name;

    @EnumValidation(enumClass = Gender.class, message = "성별 유형이 올바르지 않습니다.", ignoreCase = true)
    private String gender;

    private String mail;
    private String phoneNumber;
    private String address;

    @EnumValidation(enumClass = MemberStatus.class, message = "회원 상태 유형이 올바르지 않습니다.", ignoreCase = true)
    private String status; // enum 타입 검증 애노테이션

}
