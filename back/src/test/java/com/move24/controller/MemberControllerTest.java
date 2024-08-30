package com.move24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.move24.domain.member.dto.request.MemberJoinRequest;
import com.move24.domain.member.entity.member.Gender;
import com.move24.domain.member.entity.member.Member;
import com.move24.domain.member.entity.member.Role;
import com.move24.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("새로운 회원이 등록된다.")
    void successSignup() throws Exception {
        // given
        String userId = "skdltm12";
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId(userId)
                .password("jinkwanho12#")
                .name("관호")
                .gender("MALE")
                .mail("sksmss123@gmail.com")
                .address("서울시 양산길 33")
                .phoneNumber("010-1234-5678")
                .role("ROLE_USER")
                .build();

        String json = objectMapper.writeValueAsString(request);

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                json.getBytes()
        );

        MockMultipartFile fileRequest = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // when
        ResultActions actions = mockMvc.perform(multipart("/api/members/new")
                .file(jsonRequest)
                .file(fileRequest)
                .contentType(MULTIPART_FORM_DATA)
                .characterEncoding("UTF-8"));

        // then
        actions.andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());

        /**
         * orElse 수정 예정
         */
        Member member = memberRepository.findByUserId(userId).orElse(null);

        assertEquals(1L, memberRepository.count());
        assertEquals(request.getUserId(), Objects.requireNonNull(member).getUserId());
        assertEquals(request.getPassword(), member.getPassword());
        assertEquals(request.getName(), member.getDetails().getName());
        assertEquals(Gender.valueOf(request.getGender()), member.getDetails().getGender());
        assertEquals(request.getMail(), member.getDetails().getMail());
        assertEquals(request.getAddress(), member.getDetails().getAddress());
        assertEquals(request.getPhoneNumber(), member.getDetails().getPhoneNumber());
        assertEquals(Role.valueOf(request.getRole()), member.getDetails().getRole());
    }

    @Test
    @DisplayName("아이디를 입력하지 않으면 회원가입이 되지 않는다.")
    void unSuccessSignup() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .password("jinkwanho12#")
                .name("관호")
                .gender("MALE")
                .mail("sksmss123@gmail.com")
                .address("서울시 양산길 33")
                .phoneNumber("010-1234-5678")
                .role("ROLE_USER")
                .build();

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // when
        ResultActions actions = mockMvc.perform(multipart("/api/members/new")
                .file(jsonRequest)
                .file(file)
                .contentType(MULTIPART_FORM_DATA)
                .characterEncoding("UTF-8"));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("검증 오류입니다."))
                .andExpect(jsonPath("$.validation.userId").value("아이디는 필수로 입력해야 합니다."))
                .andDo(print());
    }
}