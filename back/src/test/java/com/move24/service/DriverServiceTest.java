package com.move24.service;

import com.move24.domain.Driver;
import com.move24.domain.Member;
import com.move24.exception.exception.PostNotFoundException;
import com.move24.repository.DriverRepository;
import com.move24.repository.MemberRepository;
import com.move24.request.DriverPostRequest;
import com.move24.response.DriverOneResponse;
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
        // given
        String driverId = "skdltm12";
        Member member = saveMember(driverId);

        DriverPostRequest request = DriverPostRequest.builder()
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
    @DisplayName("기사 게시글 1개 조회")
    void viewPostOne() {
        // given
        String driverId = "skdltm12";

        DriverOneResponse driverOneResponse = DriverOneResponse.builder()
                .driverId(driverId)
                .build();

        when(driverRepository.getDriverOne(driverId)).thenReturn(Optional.of(driverOneResponse));

        // when
        DriverOneResponse result = driverService.getDriver(driverId);

        // then
        assertEquals(driverId, result.getDriverId());
    }

    @Test
    @DisplayName("존재하지 않는 기사 게시글 조회 시 예외 발생")
    void viewPostNotFound() {

        // given
        String driverId = "skdltm12";
        when(driverRepository.getDriverOne(driverId)).thenReturn(Optional.empty());

        // expected
        assertThrows(PostNotFoundException.class, () -> {driverService.getDriver("skdltm12");});
    }

    @Test
    @DisplayName("기사 전체 게시글 조회")
    void viewPosts() {

    }
}