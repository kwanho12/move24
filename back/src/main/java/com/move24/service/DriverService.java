package com.move24.service;

import com.move24.domain.Driver;
import com.move24.domain.Member;
import com.move24.exception.exception.PostNotFoundException;
import com.move24.repository.DriverRepository;
import com.move24.repository.MemberRepository;
import com.move24.request.DriverPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DriverService {

    private final MemberRepository memberRepository;
    private final DriverRepository driverRepository;

    @Transactional
    public void write(DriverPostRequest request) {

        Member member = memberRepository.findById(request.getDriverId()).orElse(null);

        Driver driver = Driver.builder()
                .member(member)
                .experienceYear(request.getExperienceYear())
                .content(request.getContent())
                .likeCount(0)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        driverRepository.save(driver);
    }

    public Driver getDriver(String driverId) {

        Member member = memberRepository.findById(driverId).orElse(null);

        return driverRepository.findByMember(member)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 기사입니다."));
    }
}
