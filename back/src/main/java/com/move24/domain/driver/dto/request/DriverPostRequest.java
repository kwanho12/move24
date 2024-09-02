package com.move24.domain.driver.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DriverPostRequest {

    private final String driverId;

    @NotNull(message = "경력 연차는 필수로 숫자로 입력해야 합니다.")
    private final Integer experienceYear;

    @NotBlank(message = "내용은 필수로 입력해야 합니다.")
    private final String content;

    @Builder
    private DriverPostRequest(String driverId, Integer experienceYear, String content) {
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
