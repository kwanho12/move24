package com.move24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.move24.domain.Driver;
import com.move24.repository.DriverRepository;
import com.move24.request.DriverPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DriverRepository driverRepository;

    @Test
    @DisplayName("게시글 등록시 DB에 데이터가 저장된다.")
    void successPost() throws Exception {

        // given
        DriverPostRequest request = DriverPostRequest.builder()
                .driverId("skdltm12")
                .experienceYear(5)
                .content("빠르고 신속한 운송 서비스를 제공합니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/driver")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Driver driver = driverRepository.findById(request.getDriverId()).orElse(null);

        // then
        assertEquals(request.getDriverId(), Objects.requireNonNull(driver).getId());
        assertEquals(request.getExperienceYear(), driver.getExperienceYear());
        assertEquals(request.getContent(), driver.getContent());

    }
}