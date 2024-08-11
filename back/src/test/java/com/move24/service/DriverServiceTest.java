package com.move24.service;

import com.move24.domain.Driver;
import com.move24.repository.DriverRepository;
import com.move24.request.DriverPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Mock
    private DriverRepository mockDriverRepository;

    @InjectMocks
    private DriverService mockDriverService;

    @Captor
    ArgumentCaptor<Driver> driverCaptor;

    @Test
    @DisplayName("게시글 등록을 성공한다.")
    void successPost() {

        // given
        DriverPostRequest request = DriverPostRequest.builder()
                .driverId("skdltm12")
                .experienceYear(5)
                .content("빠르고 신속한 운송 서비스를 제공합니다.")
                .build();

        // when
        mockDriverService.post(request);

        // then
        Mockito.verify(mockDriverRepository, Mockito.times(1)).save(driverCaptor.capture());

        Driver savedDriver = driverCaptor.getValue();
        assertEquals(request.getDriverId(), savedDriver.getId());
        assertEquals(request.getExperienceYear(), savedDriver.getExperienceYear());
        assertEquals(request.getContent(), savedDriver.getContent());
    }
}