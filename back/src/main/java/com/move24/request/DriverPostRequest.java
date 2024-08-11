package com.move24.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DriverPostRequest {

    private String driverId;
    private int experienceYear;
    private String content;
}
