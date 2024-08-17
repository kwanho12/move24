package com.move24.controller;

import com.move24.request.DriverPostRequest;
import com.move24.request.DriverSearchCondition;
import com.move24.response.DriverOneResponse;
import com.move24.response.DriversResponse;
import com.move24.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/api/driver")
    public void post(@RequestBody DriverPostRequest request) {
        driverService.write(request);
    }

    @GetMapping("/api/driver/{driverId}")
    public DriverOneResponse getDriverOne(@PathVariable(name = "driverId") String driverId) {
        return driverService.getDriver(driverId);
    }

    // 수정(API로 요청, 응답할 때는 절대 엔티티를 사용하지 않는다 -> dto를 따로 만들어서 사용)
    @GetMapping("/api/drivers")
    public Page<DriversResponse> getDrivers(DriverSearchCondition condition, Pageable pageable) {
        return driverService.getDrivers(condition, pageable);
    }

    static class Result<T> {
        private T data;
    }
}
