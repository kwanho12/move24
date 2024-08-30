package com.move24.domain.driver.repository;

import com.move24.domain.driver.dto.request.DriverSearchServiceCondition;
import com.move24.domain.driver.dto.response.DriverOneResponse;
import com.move24.domain.driver.dto.response.DriversResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DriverRepositoryCustom {
    Optional<DriverOneResponse> getDriverOne(String driverId);

    Page<DriversResponse> getDrivers(DriverSearchServiceCondition condition, Pageable pageable);
}
