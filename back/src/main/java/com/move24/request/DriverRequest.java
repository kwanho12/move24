package com.move24.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DriverRequest {

    private String driverId;
    private int experienceYear;
    private String content;
}
