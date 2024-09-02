package com.move24.domain.driver.dto.request;

import com.move24.common.utils.ValidationUtil;
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

    public void validateBusinessPolicyException() {
        ValidationUtil.validateExperienceYear("experienceYear", experienceYear, "경력은 0~100년까지 입력 가능합니다.");
    }
}
