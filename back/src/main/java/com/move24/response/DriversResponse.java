package com.move24.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriversResponse {

    private String name;
    private int experienceYear;
    private int likeCount;
    private String fileName;

}
