package com.move24.domain.driver.controller;

import com.move24.domain.driver.dto.request.DriverPostRequest;
import com.move24.domain.driver.dto.request.DriverSearchCondition;
import com.move24.domain.driver.dto.response.DriverOneResponse;
import com.move24.domain.driver.dto.response.DriversResponse;
import com.move24.domain.driver.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/api/drivers/new")
    public void post(@RequestBody @Validated DriverPostRequest request) {
        driverService.post(request.toServiceRequest());
    }

    @GetMapping("/api/drivers/{driverId}")
    public DriverOneResponse getDriverOne(@PathVariable(name = "driverId") String driverId) {
        return driverService.getOne(driverId);
    }

    @GetMapping("/api/drivers")
    public Page<DriversResponse> getDrivers(@Validated DriverSearchCondition condition,
                                            @PageableDefault(size = 6) Pageable pageable) {
        return driverService.getList(condition.toServiceRequest(), pageable);
    }
}
