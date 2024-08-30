package com.move24.domain.driver.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DriverPostRequest {

    private final String driverId;
    private final int experienceYear;
    private final String content;

    @Builder
    private DriverPostRequest(String driverId, int experienceYear, String content) {
        this.driverId = driverId;
        this.experienceYear = experienceYear;
        this.content = content;
    }

    public DriverPostServiceRequest toServiceRequest() {
        return DriverPostServiceRequest.builder()
                .driverId(driverId)
                .experienceYear(experienceYear)
                .content(content)
                .build();
    }
}
