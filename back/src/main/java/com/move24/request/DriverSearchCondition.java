package com.move24.request;

import com.move24.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverSearchCondition {

    private String name;
    private Gender gender;
    private String mail;
    private String phoneNumber;
}
