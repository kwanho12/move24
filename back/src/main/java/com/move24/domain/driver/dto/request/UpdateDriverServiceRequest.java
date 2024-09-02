package com.move24.domain.driver.dto.request;

import lombok.Builder;
import lombok.Getter;

import static com.move24.common.utils.ValidationUtil.validateExperienceYear;

@Getter
public class UpdateDriverServiceRequest {

    private final String driverId;
    private final int experienceYear;
    private final String content;

    @Builder
    private UpdateDriverServiceRequest(String driverId, int experienceYear, String content) {
        this.driverId = driverId;
        this.experienceYear = experienceYear;
        this.content = content;
    }

    public void validateBusinessPolicyException() {
        validateExperienceYear("experienceYear", experienceYear, "경력은 0~100년까지 입력 가능합니다.");
    }
}
