package com.move24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.move24.domain.driver.dto.request.DriverPostRequest;
import com.move24.domain.driver.entity.Driver;
import com.move24.domain.driver.repository.DriverRepository;
import com.move24.domain.member.entity.image.Image;
import com.move24.domain.member.entity.member.Gender;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.entity.member.MemberDetails;
import com.move24.domain.member.entity.member.Role;
import com.move24.domain.member.repository.ImageRepository;
import com.move24.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

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
    @DisplayName("새로운 게시글이 등록된다.")
    void successPost() throws Exception {
        // given
        String driverId = "skdltm12";
        Member member = createMember(driverId);

        DriverPostRequest request = DriverPostRequest.builder()
                .driverId(driverId)
                .experienceYear(5)
                .content("빠르고 신속한 운송 서비스를 제공합니다.")
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        /**
         * orElse 수정 예정
         */
        Driver driver = driverRepository.findByMember(member).orElse(null);

        assertEquals(1L, driverRepository.count());
        assertEquals(request.getDriverId(), Objects.requireNonNull(driver).getMember().getUserId());
        assertEquals(request.getExperienceYear(), driver.getExperienceYear());
        assertEquals(request.getContent(), driver.getContent());
    }

    @Test
    @DisplayName("기사의 상세 정보를 조회한다.")
    void viewPostOne() throws Exception {
        // given
        String driverId = "skdltm12";
        Member member = createMember(driverId);
        Driver driver = createDriver(member);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
                        "/api/drivers/{driverId}", driverId)
                .contentType(APPLICATION_JSON));

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.driverId")
                        .value(driver.getMember().getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.experienceYear")
                        .value(driver.getExperienceYear()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content")
                        .value(driver.getContent()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fileName")
                        .value(member.getImage().getFileName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender")
                        .value(member.getDetails().getGender().name()))
                .andDo(MockMvcResultHandlers.print());
    }

    private Member createMember(String driverId) {
        MockMultipartFile imageFile = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        Image image = Image.create(imageFile);
        imageRepository.save(image);

        MemberDetails memberDetails = MemberDetails.builder()
                .gender(Gender.valueOf("MALE"))
                .name("관호")
                .mail("sksmss123@gmail.com")
                .phoneNumber("010-1234-5678")
                .address("서울시 양산길 33")
                .role(Role.valueOf("ROLE_USER"))
                .build();


        Member member = Member.builder()
                .userId(driverId)
                .password("rkskek12")
                .image(image)
                .details(memberDetails)
                .build();

        return memberRepository.save(member);
    }

    private Driver createDriver(Member member) {
        Driver driver = Driver.builder()
                .member(member)
                .experienceYear(10)
                .content("빠르고 신속한 운송 서비스를 제공합니다.")
                .build();
        return driverRepository.save(driver);
    }

}