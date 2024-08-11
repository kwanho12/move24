package com.move24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.move24.domain.Member;
import com.move24.enums.Gender;
import com.move24.enums.Role;
import com.move24.repository.MemberRepository;
import com.move24.request.JoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
    @DisplayName("회원가입시 DB에 데이터가 저장된다.")
    void successSignup() throws Exception {

        String memberId = "skdltm12";

        // given
        JoinRequest request = JoinRequest.builder()
                .memberId(memberId)
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

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // when
        mockMvc.perform(multipart("/api/signup")
                        .file(jsonRequest)
                        .file(file)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());

        Member member = memberRepository.findById(memberId).orElse(null);

        // then
        assertEquals(request.getMemberId(), Objects.requireNonNull(member).getId());
        assertEquals(request.getPassword(), member.getPassword());
        assertEquals(request.getName(), member.getDetails().getName());
        assertEquals(Gender.valueOf(request.getGender()), member.getDetails().getGender());
        assertEquals(request.getMail(), member.getDetails().getMail());
        assertEquals(request.getAddress(), member.getDetails().getAddress());
        assertEquals(request.getPhoneNumber(), member.getDetails().getPhoneNumber());
        assertEquals(Role.valueOf(request.getRole()), member.getDetails().getRole());
    }

    @Test
    @DisplayName("아이디를 입력하지 않으면 오류가 발생한다.")
    void unSuccessSignup() throws Exception {

        String email = "sksmss123@gmail.com";

        // given
        JoinRequest request = JoinRequest.builder()
                .password("jinkwanho12#")
                .name("관호")
                .gender("MALE")
                .mail(email)
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

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/signup")
                        .file(jsonRequest)
                        .file(file)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.memberId").value("아이디는 필수로 입력해야 합니다."))
                .andDo(print());
    }
}