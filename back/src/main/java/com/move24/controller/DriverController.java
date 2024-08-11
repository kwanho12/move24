package com.move24.controller;

import com.move24.request.DriverPostRequest;
import com.move24.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/api/driver")
    public void post(@RequestBody DriverPostRequest request) {
        driverService.post(request);
    }

    @GetMapping("/api/driver/{memberId}")
    public void getDriver(@PathVariable(name = "memberId") String memberId) {
        driverService.getDriver(memberId);
    }
}
