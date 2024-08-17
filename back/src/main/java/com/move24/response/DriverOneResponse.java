package com.move24.response;

import com.move24.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverOneResponse {

    private String driverId;
    private String name;
    private Gender gender;
    private String mail;
    private String phoneNumber;
    private int experienceYear;
    private String content;
    private int likeCount;
    private String fileName;

}
