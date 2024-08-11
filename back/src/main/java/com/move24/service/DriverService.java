package com.move24.service;

import com.move24.domain.Driver;
import com.move24.repository.DriverRepository;
import com.move24.request.DriverPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    @Transactional
    public void post(DriverPostRequest request) {

        Driver driver = Driver.builder()
                .id(request.getDriverId())
                .experienceYear(request.getExperienceYear())
                .content(request.getContent())
                .likeCount(0)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        driverRepository.save(driver);
    }

    public void getDriver(String memberId) {
        driverRepository.findById(memberId);
    }
}
