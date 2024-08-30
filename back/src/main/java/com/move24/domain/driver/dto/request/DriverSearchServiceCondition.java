package com.move24.domain.driver.dto.request;

import com.move24.domain.member.entity.member.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DriverSearchServiceCondition {

    private final String name;
    private final Gender gender;
    private final String mail;
    private final String phoneNumber;

    @Builder
    private DriverSearchServiceCondition(String name, Gender gender, String mail, String phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
    }
}
