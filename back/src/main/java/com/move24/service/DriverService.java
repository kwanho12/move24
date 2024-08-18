package com.move24.service;

import com.move24.domain.Driver;
import com.move24.domain.Member;
import com.move24.exception.exception.PostNotFoundException;
import com.move24.repository.DriverRepository;
import com.move24.repository.MemberRepository;
import com.move24.request.DriverPostRequest;
import com.move24.request.DriverSearchCondition;
import com.move24.response.DriverOneResponse;
import com.move24.response.DriversResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DriverService {

    private final MemberRepository memberRepository;
    private final DriverRepository driverRepository;

    @Transactional
    public void write(DriverPostRequest request) {

        Member member = memberRepository.findByUserId(request.getDriverId()).orElse(null);

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

    public DriverOneResponse getOne(String driverId) {
       return driverRepository.getDriverOne(driverId).orElseThrow(() -> new PostNotFoundException("기사 게시글이 존재하지 않습니다."));
    }

    public Page<DriversResponse> getList(DriverSearchCondition condition, Pageable pageable) {
        return driverRepository.getDrivers(condition, pageable);
    }


    /// 수정
    public void updateDriver(String driverId) {

        Member member = memberRepository.findByUserId(driverId).orElse(null);

        Driver driver = driverRepository.findByMember(member)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 기사입니다."));

//        return DriverOneResponse.builder().build();

    }
}
