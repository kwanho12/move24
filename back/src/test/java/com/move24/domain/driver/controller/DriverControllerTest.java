package com.move24.domain.driver.controller;

import com.move24.ControllerTestSupport;
import com.move24.domain.driver.dto.request.DriverPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DriverControllerTest extends ControllerTestSupport {

    @DisplayName("기사 게시글을 등록한다.")
    @Test
    void postDriver() throws Exception {
        // given
        DriverPostRequest request = DriverPostRequest.builder()
                .driverId("skdltm12")
                .experienceYear(10)
                .content("빠르고 신속한 서비스를 제공합니다.")
                .build();

        // expected
        mockMvc.perform(post("/api/drivers/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("기사 게시글을 등록할 때 경력 연차는 필수로 숫자로 입력해야 한다.")
    @Test
    void postWithoutExperienceYear() throws Exception {
        // given
        DriverPostRequest request = DriverPostRequest.builder()
                .driverId("skdltm12")
                .content("빠르고 신속한 서비스를 제공합니다.")
                .build();

        // expected
        mockMvc.perform(post("/api/drivers/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.experienceYear").value("경력 연차는 필수로 숫자로 입력해야 합니다."));
    }

    @DisplayName("기사 게시글을 등록할 때 내용은 필수로 숫자로 입력해야 한다.")
    @Test
    void postDriverWithoutContent() throws Exception {
        // given
        DriverPostRequest request = DriverPostRequest.builder()
                .driverId("skdltm12")
                .experienceYear(10)
                .build();

        // expected
        mockMvc.perform(post("/api/drivers/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.content").value("내용은 필수로 입력해야 합니다."));
    }

    @DisplayName("기사 게시글을 성별에 대해서 검색할 때 올바르게 입력해야 한다.")
    @Test
    void getDriversWithCorrectGender1() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("gender", "MALE");

        // expected
        mockMvc.perform(get("/api/drivers")
                        .params(params)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("기사 게시글을 성별에 대해서 검색할 때 올바르게 입력해야 한다.")
    @Test
    void getDriversWithCorrectGender2() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("gender", "FEMALE");

        // expected
        mockMvc.perform(get("/api/drivers")
                        .params(params)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("기사 게시글을 성별에 대해서 검색할 때 올바르게 입력해야 한다.")
    @Test
    void getDriversWithWrongGender() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("gender", "남자");

        // expected
        mockMvc.perform(get("/api/drivers")
                        .params(params)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.gender").value("성별 유형이 올바르지 않습니다."));
    }

}