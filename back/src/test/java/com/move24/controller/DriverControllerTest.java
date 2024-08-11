package com.move24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.move24.domain.Driver;
import com.move24.domain.Image;
import com.move24.domain.Member;
import com.move24.domain.MemberDetails;
import com.move24.enums.MemberStatus;
import com.move24.enums.Role;
import com.move24.repository.DriverRepository;
import com.move24.repository.ImageRepository;
import com.move24.repository.MemberRepository;
import com.move24.request.DriverPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.move24.enums.Gender.valueOf;
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

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("게시글 등록시 DB에 데이터가 저장된다.")
    void successPost() throws Exception {
        // given
        String driverId = "skdltm12";
        Member member = getMember(driverId);

        DriverPostRequest request = DriverPostRequest.builder()
                .driverId(driverId)
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

        Driver driver = driverRepository.findByMember(member).orElse(null);

        // then
        assertEquals(1L, driverRepository.count());
        assertEquals(request.getDriverId(), Objects.requireNonNull(driver).getMember().getId());
        assertEquals(request.getExperienceYear(), driver.getExperienceYear());
        assertEquals(request.getContent(), driver.getContent());
    }

    private Member getMember(String driverId) {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        Image image = new Image(file);
        imageRepository.save(image);

        MemberDetails memberDetails = MemberDetails.builder()
                .gender(valueOf("MALE"))
                .name("관호")
                .mail("sksmss123@gmail.com")
                .phoneNumber("010-1234-5678")
                .address("서울시 양산길 33")
                .role(Role.valueOf("ROLE_USER"))
                .build();


        Member member = Member.builder()
                .id(driverId)
                .password("rkskek12")
                .image(image)
                .status(MemberStatus.ACTIVE)
                .details(memberDetails)
                .loginDate(LocalDateTime.now())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        return memberRepository.save(member);
    }
}