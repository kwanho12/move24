package com.move24.repository;

import com.move24.request.DriverSearchCondition;
import com.move24.response.DriverOneResponse;
import com.move24.response.DriversResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DriverRepositoryCustom {
    Optional<DriverOneResponse> getDriverOne(String driverId);

    Page<DriversResponse> getDrivers(DriverSearchCondition condition, Pageable pageable);
}
