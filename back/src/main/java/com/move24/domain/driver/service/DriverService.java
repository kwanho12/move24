package com.move24.domain.driver.service;

import com.move24.domain.driver.dto.request.DriverPostServiceRequest;
import com.move24.domain.driver.dto.request.DriverSearchServiceCondition;
import com.move24.domain.driver.dto.response.DriverOneResponse;
import com.move24.domain.driver.dto.response.DriversResponse;
import com.move24.domain.driver.entity.Driver;
import com.move24.domain.driver.exception.DriverNotFoundException;
import com.move24.domain.driver.repository.DriverRepository;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DriverService {

    private final MemberRepository memberRepository;
    private final DriverRepository driverRepository;

    @Transactional
    public Driver post(DriverPostServiceRequest request) {
        request.validateBusinessPolicyException();

        Member member = memberRepository.findByUserId(request.getDriverId())
                .orElseThrow(() -> new DriverNotFoundException("회원이 존재하지 않습니다."));

        Driver driver = Driver.create(member, request);
        return driverRepository.save(driver);
    }

    public DriverOneResponse getOne(String driverId) {
        return driverRepository.getDriverOne(driverId)
                .map(driverOneResponse -> {
                    if (driverOneResponse.getAveragePoint() == null) {
                        driverOneResponse.setAveragePoint(0.0);
                    }
                    return driverOneResponse;
                })
                .orElseThrow(() -> new DriverNotFoundException("기사 게시글이 존재하지 않습니다."));
    }

    public Page<DriversResponse> getList(DriverSearchServiceCondition condition, Pageable pageable) {
        Page<DriversResponse> pageResponse = driverRepository.getDrivers(condition, pageable);

        List<DriversResponse> content = pageResponse.getContent().stream()
                .peek(driversResponse -> {
                    if (driversResponse.getAveragePoint() == null) {
                        driversResponse.setAveragePoint(0.0);
                    }
                })
                .toList();
        return new PageImpl<>(content, pageable, pageResponse.getTotalElements());
    }

    /// 수정
//    public void updateDriver(UpdateDriverPostServiceRequest request) {
//
//        Member member = memberRepository.findByUserId(driverId)
//                .orElseThrow(() -> new DriverNotFoundException("회원이 존재하지 않습니다."));
//
//        Driver driver = driverRepository.findByMember(member)
//                .orElseThrow(() -> new DriverNotFoundException("존재하지 않는 기사입니다."));
//
//        driver.update();
//
//    }
}
