package com.move24.service;

import com.move24.domain.Driver;
import com.move24.domain.Member;
import com.move24.exception.exception.PostNotFoundException;
import com.move24.repository.DriverRepository;
import com.move24.repository.MemberRepository;
import com.move24.request.DriverPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


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
    @DisplayName("게시글 등록을 성공한다.")
    void successPost() {

        String driverId = "skdltm12";

        // given
        DriverPostRequest request = DriverPostRequest.builder()
                .driverId(driverId)
                .experienceYear(5)
                .content("빠르고 신속한 운송 서비스를 제공합니다.")
                .build();

        Member member = Member.builder()
                .id(driverId)
                .build(); // 테스트를 위한 Member 객체 생성

        when(memberRepository.findById(driverId)).thenReturn(Optional.of(member));

        // when
        driverService.write(request);

        // then
        verify(memberRepository, times(1)).findById(driverId);
        verify(driverRepository, times(1)).save(driverCaptor.capture());

        Driver savedDriver = driverCaptor.getValue();

        assertEquals(request.getDriverId(), savedDriver.getMember().getId());
        assertEquals(request.getExperienceYear(), savedDriver.getExperienceYear());
        assertEquals(request.getContent(), savedDriver.getContent());
    }

    @Test
    @DisplayName("기사 게시글 1개 조회")
    void viewPostOne() {
        // given
        String driverId = "skdltm12";
        Member member = saveMember(driverId);

        Driver driver = Driver.builder()
                .member(member)
                .experienceYear(5)
                .content("빠르고 신속한 운송 서비스를 제공합니다.")
                .build();

        when(memberRepository.findById(driverId)).thenReturn(Optional.of(member));
        when(driverRepository.findByMember(member)).thenReturn(Optional.of(driver));

        // when
        Driver result = driverService.getDriver(driverId);

        // then
        assertEquals(driver.getMember().getId(), result.getMember().getId());
    }

    private Member saveMember(String driverId) {
        return Member.builder()
                .id(driverId)
                .build();
    }

    @Test
    @DisplayName("존재하지 않는 기사 조회 시 예외 발생")
    void viewPostNotFound() {
        // given
        when(memberRepository.findById(anyString())).thenReturn(Optional.empty());
        when(driverRepository.findByMember(any())).thenReturn(Optional.empty());

        // when & then
        assertThrows(PostNotFoundException.class, () -> {driverService.getDriver("skdltm12");});
    }
}