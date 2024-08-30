package com.move24.domain.driver.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DriversResponse {

    private String driverId;
    private String name;
    private Integer experienceYear;
    private String fileName;
    private Double averagePoint;

    @Builder
    private DriversResponse(String driverId, String name, int experienceYear, String fileName, Double averagePoint) {
        this.driverId = driverId;
        this.name = name;
        this.experienceYear = experienceYear;
        this.fileName = fileName;
        this.averagePoint = averagePoint;
    }

    public void setAveragePoint(Double averagePoint) {
        this.averagePoint = averagePoint;
    }
}
