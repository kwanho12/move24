package com.move24.controller;

import com.move24.request.DriverRequest;
import com.move24.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/api/driver")
    public void post(@RequestBody DriverRequest request) {
        driverService.post(request);
    }
}
