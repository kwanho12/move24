package com.move24.domain.member.controller;

import com.move24.domain.ControllerTestSupport;
import com.move24.domain.member.dto.request.MemberJoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTestSupport {

    @DisplayName("신규 회원을 등록한다.")
    @Test
    void createMember() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId("skdltm12")
                .password("rkskek12#")
                .name("김천재")
                .gender("MALE")
                .mail("skdltm12@naver.com")
                .address("서울시 양산길")
                .phoneNumber("010-2222-2222")
                .role("ROLE_USER")
                .build();

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile fileRequest = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/members/new")
                        .file(jsonRequest)
                        .file(fileRequest)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("신규 회원을 등록할 때 아이디는 필수 값이다.")
    @Test
    void createMemberWithoutUserId() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .password("rkskek12#")
                .name("김천재")
                .gender("MALE")
                .mail("skdltm12@naver.com")
                .address("서울시 양산길")
                .phoneNumber("010-2222-2222")
                .role("ROLE_USER")
                .build();

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile fileRequest = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/members/new")
                        .file(jsonRequest)
                        .file(fileRequest)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.userId").value("아이디를 입력해주세요."));
    }

    @DisplayName("신규 회원을 등록할 때 비밀번호는 필수 값이다.")
    @Test
    void createMemberWithoutPassword() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId("skdltm12")
                .name("김천재")
                .gender("MALE")
                .mail("skdltm12@naver.com")
                .address("서울시 양산길")
                .phoneNumber("010-2222-2222")
                .role("ROLE_USER")
                .build();

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile fileRequest = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/members/new")
                        .file(jsonRequest)
                        .file(fileRequest)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.password").value("비밀번호를 입력해주세요."));
    }

    @DisplayName("신규 회원을 등록할 때 이름은 필수 값이다.")
    @Test
    void createMemberWithoutName() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId("skdltm12")
                .password("rkskek12#")
                .gender("MALE")
                .mail("skdltm12@naver.com")
                .address("서울시 양산길")
                .phoneNumber("010-2222-2222")
                .role("ROLE_USER")
                .build();

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile fileRequest = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/members/new")
                        .file(jsonRequest)
                        .file(fileRequest)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.name").value("이름을 입력해주세요."));
    }

    @DisplayName("신규 회원을 등록할 때 올바른 성별 유형을 입력해야 한다.")
    @Test
    void createMemberWithWrongGender() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId("skdltm12")
                .password("rkskek12#")
                .name("김천재")
                .gender("남자")
                .mail("skdltm12@naver.com")
                .address("서울시 양산길")
                .phoneNumber("010-2222-2222")
                .role("ROLE_USER")
                .build();

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile fileRequest = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/members/new")
                        .file(jsonRequest)
                        .file(fileRequest)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.gender").value("성별 유형이 올바르지 않습니다."));
    }

    @DisplayName("신규 회원을 등록할 때 이메일은 필수 값이다.")
    @Test
    void createMemberWithoutEmail() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId("skdltm12")
                .password("rkskek12#")
                .name("김천재")
                .gender("MALE")
                .address("서울시 양산길")
                .phoneNumber("010-2222-2222")
                .role("ROLE_USER")
                .build();

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile fileRequest = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/members/new")
                        .file(jsonRequest)
                        .file(fileRequest)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.mail").value("이메일을 입력해주세요."));
    }

    @DisplayName("신규 회원을 등록할 때 주소는 필수 값이다.")
    @Test
    void createMemberWithoutAddress() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId("skdltm12")
                .password("rkskek12#")
                .name("김천재")
                .gender("MALE")
                .mail("skdltm12@naver.com")
                .phoneNumber("010-2222-2222")
                .role("ROLE_USER")
                .build();

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile fileRequest = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/members/new")
                        .file(jsonRequest)
                        .file(fileRequest)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.address").value("주소를 입력해주세요."));
    }

    @DisplayName("신규 회원을 등록할 때 휴대폰 번호는 필수 값이다.")
    @Test
    void createMemberWithoutPhoneNumber() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId("skdltm12")
                .password("rkskek12#")
                .name("김천재")
                .gender("MALE")
                .address("서울시 양산길")
                .mail("skdltm12@naver.com")
                .role("ROLE_USER")
                .build();

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile fileRequest = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/members/new")
                        .file(jsonRequest)
                        .file(fileRequest)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.phoneNumber").value("휴대폰 번호를 입력해주세요."));
    }

    @DisplayName("신규 회원을 등록할 때 올바른 Role 유형을 입력해야 한다.")
    @Test
    void createMemberWithWrongRole() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .userId("skdltm12")
                .password("rkskek12#")
                .name("김천재")
                .gender("MALE")
                .mail("skdltm12@naver.com")
                .address("서울시 양산길")
                .phoneNumber("010-2222-2222")
                .role("ROLE_MEMBER")
                .build();

        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes()
        );

        MockMultipartFile fileRequest = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/members/new")
                        .file(jsonRequest)
                        .file(fileRequest)
                        .contentType(MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("parameter 미입력 or 잘못된 유형 검증 오류입니다."))
                .andExpect(jsonPath("$.responseData.role").value("ROLE 유형이 올바르지 않습니다."));
    }
}