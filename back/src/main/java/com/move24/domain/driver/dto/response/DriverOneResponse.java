package com.move24.domain.driver.dto.response;

import com.move24.domain.member.entity.member.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DriverOneResponse {

    private String driverId;
    private String name;
    private Gender gender;
    private String mail;
    private String phoneNumber;
    private int experienceYear;
    private String content;
    private String fileName;
    private Double averagePoint;

    @Builder
    private DriverOneResponse(String driverId, String name, Gender gender, String mail, String phoneNumber, int experienceYear, String content, String fileName, Double averagePoint) {
        this.driverId = driverId;
        this.name = name;
        this.gender = gender;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.experienceYear = experienceYear;
        this.content = content;
        this.fileName = fileName;
        this.averagePoint = averagePoint;
    }

    public void setAveragePoint(Double averagePoint) {
        this.averagePoint = averagePoint;
    }
}
