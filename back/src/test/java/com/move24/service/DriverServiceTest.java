package com.move24.service;

import com.move24.domain.driver.dto.request.DriverPostServiceRequest;
import com.move24.domain.driver.dto.response.DriverOneResponse;
import com.move24.domain.driver.entity.Driver;
import com.move24.domain.driver.exception.DriverNotFoundException;
import com.move24.domain.driver.repository.DriverRepository;
import com.move24.domain.driver.service.DriverService;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @Captor
    ArgumentCaptor<Driver> driverCaptor;

    @Test
    @DisplayName("새로운 게시글이 등록된다.")
    void successPost() {
        // given
        String driverId = "skdltm12";
        Member member = saveMember(driverId);

        DriverPostServiceRequest request = DriverPostServiceRequest.builder()
                .driverId(driverId)
                .experienceYear(5)
                .content("빠르고 신속한 운송 서비스를 제공합니다.")
                .build();

        when(memberRepository.findByUserId(driverId)).thenReturn(Optional.of(member));

        // when
        driverService.write(request);

        // then
        verify(memberRepository, times(1)).findByUserId(driverId);
        verify(driverRepository, times(1)).save(driverCaptor.capture());

        Driver savedDriver = driverCaptor.getValue();

        assertEquals(request.getDriverId(), savedDriver.getMember().getUserId());
        assertEquals(request.getExperienceYear(), savedDriver.getExperienceYear());
        assertEquals(request.getContent(), savedDriver.getContent());
    }

    private Member saveMember(String driverId) {
        return Member.builder()
                .userId(driverId)
                .build();
    }

    @Test
    @DisplayName("기사의 상세 정보를 조회한다.")
    void viewPostOne() {
        // given
        String driverId = "skdltm12";

        DriverOneResponse driverOneResponse = DriverOneResponse.builder()
                .driverId(driverId)
                .build();

        when(driverRepository.getDriverOne(driverId)).thenReturn(Optional.of(driverOneResponse));

        // when
        DriverOneResponse result = driverService.getOne(driverId);

        // then
        assertEquals(driverId, result.getDriverId());
    }

    @Test
    @DisplayName("존재하지 않는 기사 게시글을 조회할 수 없다.")
    void viewPostNotFound() {

        // given
        String driverId = "skdltm12";
        when(driverRepository.getDriverOne(driverId)).thenReturn(Optional.empty());

        // expected
        assertThrows(DriverNotFoundException.class, () -> {driverService.getOne("skdltm12");});
    }

}