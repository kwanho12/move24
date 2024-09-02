package com.move24.domain.driver.dto.request;

import com.move24.common.validator.EnumValidation;
import com.move24.domain.member.entity.member.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DriverSearchCondition {

    private final String name;

    @EnumValidation(enumClass = Gender.class, message = "성별 유형이 올바르지 않습니다.", ignoreCase = true)
    private final String gender;

    private final String mail;

    private final String phoneNumber;

    @Builder
    private DriverSearchCondition(String name, String gender, String mail, String phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
    }

    public DriverSearchServiceCondition toServiceRequest() {
        return DriverSearchServiceCondition.builder()
                .name(name)
                .gender(gender)
                .mail(mail)
                .phoneNumber(phoneNumber)
                .build();
    }
}
