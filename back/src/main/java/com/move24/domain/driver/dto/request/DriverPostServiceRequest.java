package com.move24.domain.driver.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DriverPostServiceRequest {

    private final String driverId;
    private final int experienceYear;
    private final String content;

    @Builder
    private DriverPostServiceRequest(String driverId, int experienceYear, String content) {
        this.driverId = driverId;
        this.experienceYear = experienceYear;
        this.content = content;
    }
}
