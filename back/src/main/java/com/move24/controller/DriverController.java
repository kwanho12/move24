package com.move24.controller;

import com.move24.domain.Driver;
import com.move24.request.DriverPostRequest;
import com.move24.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/api/driver")
    public void post(@RequestBody DriverPostRequest request) {
        driverService.write(request);
    }

    @GetMapping("/api/drivers")
    public List<Driver> getDrivers() {
        return null;
    }

    @GetMapping("/api/driver/{driverId}")
    public Driver getDriver(@PathVariable(name = "driverId") String driverId) {
        return driverService.getDriver(driverId);
    }
}
